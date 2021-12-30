package com.anish.screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.Direction;
import com.anish.calabashbros.Fog;
import com.anish.calabashbros.Heart;
import com.anish.calabashbros.Monster;
import com.anish.calabashbros.Star;
import com.anish.calabashbros.Type;
import com.anish.calabashbros.World;

import asciiPanel.AsciiPanel; 

public class WorldScreen implements Screen {

    public static int WIN_POINT = 5;
    public static World world;
    public static Calabash[] bro;
    public Monster monster;
    public Heart heart;
    public Star star;
    public Fog fog;
    public static Integer[][] MAP;

    public boolean victory = false;

    public WorldScreen() {

        if (MAP == null) {
            MAP = new Integer[World.WIDTH][World.HEIGHT];
        }

        for(int i=0;i<World.WIDTH;i++)
        {
            for(int j=0;j<World.HEIGHT;j++)
            {
                MAP[i][j] = 0;
            }
        }

        new Thread(new UpdateMap()).start();

        world = new World();

        bro = new Calabash[4];

        bro[0] = new Calabash(world);
        bro[1] = new Calabash(world);
        bro[2] = new Calabash(world);
        bro[3] = new Calabash(world);

        fog = new Fog(world);

        world.put(bro[0], 3, 3);
        world.put(bro[1], 3, 5);
        world.put(bro[2], 5, 3);
        world.put(bro[3], 5, 5);

        new Thread(monster).start();

        new Thread(new MonsPlant()).start();

        new Thread(new StarPlant()).start();

        new Thread(new HeartPlant()).start();
    }
    /*
    public WorldScreen(boolean load) {
        world = new World();
        fog = new Fog(world);
        world.load();
    
        for (int i = 0; i < World.WIDTH; i++) {
            for (int j = 0; j < World.HEIGHT; j++) {
                world.put(new Floor(world), i, j);
                
                if(world.map[i][j] == 0)
                {
                    world.put(new Floor(world), i, j);
                }
                else if(world.map[i][j] == 1)
                {
                    world.put(new Background(world), i, j);
                }
                else if(world.map[i][j] == 2)
                {
                    world.put(new Wall(world), i, j);
                }
                else if(world.map[i][j] == 3)
                {
                    monster = new Monster(world);
                    world.put(monster, i, j);
                    new Thread(monster).start();
                }
                else if(world.map[i][j] == 4)
                {
                    bro = new Calabash(world);
                    world.put( bro, i, j);
                }
                else if(world.map[i][j] == 5)
                {
                    world.put(new Star(world), i, j);
                }
                else if(world.map[i][j] == 6)
                {
                    world.put(new Heart(world), i, j);
                }
                
            }
        }
        
        new Thread(new MonsPlant()).start();

        new Thread(new StarPlant()).start();

        new Thread(new HeartPlant()).start();

    }*/

    private class UpdateMap implements Runnable{

        @Override
        public void run() {
            while(true)
            {
                try{
                    Thread.sleep(100); 
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                try {
                    world.save();
                    for(int i=0;i<World.WIDTH;i++)
                    {
                        for(int j=0;j<World.HEIGHT;j++)
                        {
                            MAP[i][j] = world.map[i][j];
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        }

    }

    private class MonsPlant implements Runnable{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(10000); 
                }catch(Exception e){
                    e.printStackTrace();
                }
                Random r = new Random();
                int posX = r.nextInt(World.WIDTH-2);
                int posY = r.nextInt(World.HEIGHT-2);
                if(world.checkType(posX+2, posY+2) == Type.FLOOR )
                {
                    monster = new Monster(world); 
                    world.put(monster, posX+2, posY+2); 
                    new Thread(monster).start();
                }
            }
        }
    }

    private class StarPlant implements Runnable{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(10000); 
                }catch(Exception e){
                    e.printStackTrace();
                }
                Random r = new Random();
                int posX = r.nextInt(World.WIDTH-2);
                int posY = r.nextInt(World.HEIGHT-2);
                if(world.checkType(posX+2, posY+2) == Type.FLOOR )
                {
                    star = new Star(world); 
                    world.put(star, posX+2, posY+2);
                }
            }
        }
    }

    private class HeartPlant implements Runnable{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(10000); 
                }catch(Exception e){
                    e.printStackTrace();
                }
                Random r = new Random();
                int posX = r.nextInt(World.WIDTH-2);
                int posY = r.nextInt(World.HEIGHT-2);
                if(world.checkType(posX+2, posY+2) == Type.FLOOR )
                {
                    heart = new Heart(world); 
                    world.put(heart, posX+2, posY+2);
                }
            }
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                int absx = x - bro[0].getX();
                int absy = y - bro[0].getY();
                if( Math.abs(absx) + Math.abs(absy) < 6 )
                {
                    terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
                }
                else
                {
                    terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
                    //terminal.write(fog.getGlyph(), x, y, fog.getColor());
                }
            }
        }
        displayMessages(terminal);
    }

    public void displayMessages(AsciiPanel terminal) {
       
        String s1 = Integer.toString(bro[0].lives);
        String s2 = Integer.toString(bro[1].lives);
        String s3 = Integer.toString(bro[2].lives);
        String s4 = Integer.toString(bro[3].lives);

        terminal.write("playerA hp:", World.WIDTH + 1, 1);

        terminal.write( s1 , World.WIDTH + 13 , 1);
        
        terminal.write("playerB hp:", World.WIDTH + 1, 3);

        terminal.write( s2 , World.WIDTH + 13 , 3);

        terminal.write("playerC hp:", World.WIDTH + 1, 5);

        terminal.write( s3 , World.WIDTH + 13 , 5);

        terminal.write("playerD hp:", World.WIDTH + 1, 7);

        terminal.write( s4 , World.WIDTH + 13 , 7);
        
    }
    
    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        if(world.WinOrLose == 0)
        {
            return new LoseScreen();
        }
        else if(world.WinOrLose == 1)
        {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_W:
                    //bro.moveTo(Direction.UP);
                    break;
                case KeyEvent.VK_A:
                    //bro.moveTo(Direction.LEFT);
                    break;
                case KeyEvent.VK_S:
                    //bro.moveTo(Direction.DOWN);
                    break;
                case KeyEvent.VK_D:
                    //bro.moveTo(Direction.RIGHT);
                    break;
                case KeyEvent.VK_J:
                    //bro.shoot();
                    break;
                case KeyEvent.VK_P:
                    world.save();
                    return new LoseScreen();
            }
        }
        else if(world.WinOrLose == 2)
        {
            return new WinScreen();
        }
        return this;
    }

    public Integer[][] getMap()
    {
        return world.map;
    }

    @Override
    public void action(String id, String keycode) throws IOException {
        int ID = Integer.valueOf(id);
        int KeyCode = Integer.valueOf(keycode);
        if(KeyCode == 87 )  //    w
        {
            bro[ID].moveTo(Direction.UP);
        }
        else if(KeyCode == 65)//    a
        {
            bro[ID].moveTo(Direction.LEFT);
        }
        else if(KeyCode == 83)//    s
        {
            bro[ID].moveTo(Direction.DOWN);
        }
        else if(KeyCode == 68)//    d
        {
            bro[ID].moveTo(Direction.RIGHT);
        }
        else if(KeyCode == 74)//    j
        {
            bro[ID].shoot();
        }
    }

}
