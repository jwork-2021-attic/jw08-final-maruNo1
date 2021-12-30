import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
//import java.util.Timer;
//import java.util.TimerTask;
import com.anish.calabashbros.Background;
import com.anish.calabashbros.BigBullet;
import com.anish.calabashbros.Bullet;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Heart;
import com.anish.calabashbros.Monster;
import com.anish.calabashbros.Star;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.World;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;


public class Client extends JFrame {

    public int [][] MAP;
    private AsciiPanel terminal;

    private Selector selector = null;
    static final int port = 9999;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel sc = null;
    private int id = 1;
    
    private Wall wall;
    private World world;
    private Floor floor;
    private Background background;
    private Monster monster;
    private Calabash calabash;
    private Star star;
    private Heart heart;
    private Bullet bullet;
    private BigBullet bigBullet;
    private int[] hp = new int[4];
    private boolean flag = true;

    public Client() throws InterruptedException {
        super();
        //初始化地图
        if (MAP == null) {
            MAP = new int [World.WIDTH][World.HEIGHT];
        }

        world = new World();
        star = new Star(world);
        wall = new Wall(world);
        floor = new Floor(world);
        heart = new Heart(world);
        monster = new Monster(world);
        calabash = new Calabash(world);
        background = new Background(world);
        bullet = new Bullet(world);
        bigBullet = new BigBullet(world);

        terminal = new AsciiPanel(World.WIDTH + 20 , World.HEIGHT, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
    }
    
    public void init() throws IOException
    {
        selector = Selector.open();
        //连接远程主机的IP和端口
        sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",port));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    if(flag == true)
                    {
                        int t = e.getKeyCode();
                        String msg = id + " " + t;
                        sc.write(charset.encode(msg));
                        repaint();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {}
            
        });

        //开辟一个新线程来读取从服务器端的数据
        new Thread(new ClientThread()).start();
    }
    
    private class ClientThread implements Runnable
    {
        public void run()
        {
            try
            {
                while(flag) {
                    int readyChannels = selector.select();
                    if(readyChannels == 0) continue; 
                    Set selectedKeys = selector.selectedKeys();  //可以通过这个方法，知道可用通道的集合
                    Iterator keyIterator = selectedKeys.iterator();
                    while(keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            }
            catch (IOException io)
            {}
        }

        private void dealWithSelectionKey(SelectionKey sk) throws IOException {
            if(sk.isReadable()) // 读服务器端的信息
            {
                //使用 NIO 读取 Channel中的数据，这个和全局变量sc是一样的，因为只注册了一个SocketChannel
                //sc既能写也能读，这边是读
                SocketChannel sc = (SocketChannel)sk.channel();
                
                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while(sc.read(buff) > 0)
                {
                    buff.flip();
                    content += charset.decode(buff);
                }
                if(content.length() < 10) //如果收到的信息长度小于10
                {
                    System.out.println(content);
                    id = Integer.valueOf(content);
                    System.out.println("My id is "+id);
                }
                else // 如果大于10说明是地图
                {
                    String[] arr = content.split(" ");

                    for(int i=0;i<World.WIDTH;i++)
                    {
                        for(int j=0;j<World.HEIGHT;j++)
                        {
                            MAP[i][j] = Integer.valueOf(arr[i*World.HEIGHT+j]);
                        }
                    }
                
                    for(int i=0;i<4;i++)
                    {
                        hp[i] = Integer.valueOf(arr[World.HEIGHT*World.WIDTH + i]);
                    }
                    repaint();
                    if(hp[id]<=0)
                    {
                        paintLose();
                        System.out.print("You Die!!!");
                        flag = false;
                    }
                    int kill = 0;
                    for(int i=0;i<4;i++)
                    {
                        if(i==id)
                        {
                            continue;
                        }
                        if(hp[i]<=0)
                        {
                            kill++;
                        }
                    }
                    if(kill == 3)
                    {
                        paintWin();
                        System.out.print("You Win!!!");
                        flag = false;
                    }
                }
                sk.interestOps(SelectionKey.OP_READ);
            }
            
        }
    }
    
    @Override
    public void repaint() {
        terminal.clear();
        displayOutput(terminal);
        super.repaint();
    }


    public void paintLose() {
        terminal.clear();
        terminal.write("YOU DIE !", 1, 1);
        super.repaint();
    }

    public void paintWin() {
        terminal.clear();
        terminal.write("YOU WIN !", 1, 1);
        super.repaint();
    }

    public void displayOutput2(AsciiPanel terminal) {
        terminal.clear();
        String s1 = Integer.toString(hp[0]);
        String s2 = Integer.toString(hp[1]);
        String s3 = Integer.toString(hp[2]);
        String s4 = Integer.toString(hp[3]);

        terminal.write("DASDDAS hp:", World.WIDTH + 1, 1);

        terminal.write( s1 , World.WIDTH + 13 , 1);

        terminal.write("playerB hp:", World.WIDTH + 1, 3);

        terminal.write( s2 , World.WIDTH + 13 , 3);

        terminal.write("playerC hp:", World.WIDTH + 1, 5);

        terminal.write( s3 , World.WIDTH + 13 , 5);

        terminal.write("playerD hp:", World.WIDTH + 1, 7);

        terminal.write( s4 , World.WIDTH + 13 , 7);
        
    }

    public void displayOutput(AsciiPanel terminal) {
        
        for(int i=0;i<World.WIDTH;i++)
        {
            for(int j=0;j<World.HEIGHT;j++)
            {
                if(MAP[i][j]==0)
                {
                    terminal.write(floor.getGlyph(), i, j, floor.getColor() );
                }
                else if(MAP[i][j]==1)
                {
                    terminal.write(background.getGlyph(), i, j, background.getColor() );
                }
                else if(MAP[i][j]==2)
                {
                    terminal.write(wall.getGlyph(), i, j, wall.getColor() );
                }
                else if(MAP[i][j]==3)
                {
                    terminal.write(monster.getGlyph(), i, j, monster.getColor() );
                }
                else if(MAP[i][j]==4)
                {
                    terminal.write(calabash.getGlyph(), i, j, calabash.getColor() );
                }
                else if(MAP[i][j]==5)
                {
                    terminal.write(star.getGlyph(), i, j, star.getColor() );
                }
                else if(MAP[i][j]==6)
                {
                    terminal.write(heart.getGlyph(), i, j, heart.getColor() );
                }
                else if(MAP[i][j]==7)
                {
                    terminal.write(bullet.getGlyph(), i, j, bullet.getColor() );
                }
                else if(MAP[i][j]==8)
                {
                    terminal.write(bigBullet.getGlyph(), i, j, bigBullet.getColor() );
                }
            }
        }

        String s1 = Integer.toString(hp[0]);
        String s2 = Integer.toString(hp[1]);
        String s3 = Integer.toString(hp[2]);
        String s4 = Integer.toString(hp[3]);

        terminal.write("playerA hp:", World.WIDTH + 1, 1);

        terminal.write( s1 , World.WIDTH + 13 , 1);

        terminal.write("playerB hp:", World.WIDTH + 1, 3);

        terminal.write( s2 , World.WIDTH + 13 , 3);

        terminal.write("playerC hp:", World.WIDTH + 1, 5);

        terminal.write( s3 , World.WIDTH + 13 , 5);

        terminal.write("playerD hp:", World.WIDTH + 1, 7);

        terminal.write( s4 , World.WIDTH + 13 , 7);
        
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Client app = new Client();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.init();
    }

}

// 开始-结束-失败-胜利界面