

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import javax.swing.JFrame;

import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import com.anish.calabashbros.World;
import com.anish.screen.Screen;
import com.anish.screen.WorldScreen;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class Main1 extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;
    private int usernum = 0;

    private Selector selector = null;
    static final int port = 9999;
    private Charset charset = Charset.forName("UTF-8");

    public Main1() throws InterruptedException {
        super();
        terminal = new AsciiPanel(World.WIDTH + 20 , World.HEIGHT, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        screen = new WorldScreen();
        addKeyListener(this);

        Timer timer = new Timer(); // 主流程控制
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                while(true)
                {    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
            }
        };
        timer.schedule(timerTask,0,100);

        repaint();

    }

    public void init() throws IOException
    {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        //非阻塞的方式
        server.configureBlocking(false);
        //注册到选择器上，设置为监听状态
        server.register(selector, SelectionKey.OP_ACCEPT);
        
        System.out.println("Server is listening now...");

        new Thread(new SendMap()).start();
        
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue; 
            Set selectedKeys = selector.selectedKeys();  //可以通过这个方法，知道可用通道的集合
            Iterator keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {
                SelectionKey sk = (SelectionKey) keyIterator.next();
                keyIterator.remove();
                dealWithSelectionKey(server,sk);
            }
        }
    }

    private class SendMap implements Runnable
    {
        public void run()
        {
            while(true)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                String MapMessage = "";
                
                for(int i=0;i<World.WIDTH;i++)
                {
                    for(int j=0;j <World.HEIGHT;j++)
                    {
                        String s = String.valueOf(WorldScreen.MAP[i][j]);
                        MapMessage += s;
                        MapMessage += " ";
                    }
                } 

                for(int i=0;i<4;i++)
                {
                    MapMessage += WorldScreen.bro[i].lives;
                    MapMessage += " ";
                }

                try {
                    BroadCast(selector, MapMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void dealWithSelectionKey(ServerSocketChannel server,SelectionKey sk) throws IOException {

        if(sk.isAcceptable())
        {
            SocketChannel sc = server.accept();
            //非阻塞模式
            sc.configureBlocking(false);
            //注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个SocketChannel处理
            sc.register(selector, SelectionKey.OP_READ);
            //将此对应的channel设置为准备接受其他客户端请求
            sk.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening from client :" + sc.getRemoteAddress());
            String id = String.valueOf(usernum);
            usernum++;
            System.out.println("Welcome No."+id+" pllayer!");
            sc.write(charset.encode(id));
        }
        //处理来自客户端的数据读取请求
        if(sk.isReadable())
        {
            //返回该SelectionKey对应的 Channel，其中有数据需要读取
            SocketChannel sc = (SocketChannel)sk.channel(); 
            ByteBuffer buff = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try
            {
                while(sc.read(buff) > 0)
                {
                    buff.flip();
                    content.append(charset.decode(buff));
                    
                }
                System.out.println("Server is listening from client " + sc.getRemoteAddress() + " data rev is: " + content);

                String msg = content.toString();

                String[] arr = msg.split(" ");

                screen.action(arr[0],arr[1]);

                //将此对应的channel设置为准备下一次接受数据
                sk.interestOps(SelectionKey.OP_READ);
            }
            catch (IOException io)
            {
                sk.cancel();
                if(sk.channel() != null)
                {
                    sk.channel().close();
                }
            }

            String MapMessage = "";
        
            for(int i=0;i<World.WIDTH;i++)
            {
                for(int j=0;j<World.HEIGHT;j++)
                {
                    String s = String.valueOf(WorldScreen.MAP[i][j]);
                    MapMessage += s;
                    MapMessage += " ";
                }
            }

            for(int i=0;i<4;i++)
            {
                MapMessage += WorldScreen.bro[i].lives;
                MapMessage += " ";
            }
            
            BroadCast(selector, MapMessage);
        }

    }
    
    public void BroadCast(Selector selector,  String content) throws IOException {
        //广播数据到所有的SocketChannel中
        for(SelectionKey key : selector.keys())
        {
            Channel targetchannel = key.channel();
            
            if(targetchannel instanceof SocketChannel )
            {
                SocketChannel dest = (SocketChannel)targetchannel;
                dest.write(charset.encode(content));
            }
        }
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            screen = screen.respondToUserInput(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }      
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Main1 app = new Main1();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.init();
    }
}
