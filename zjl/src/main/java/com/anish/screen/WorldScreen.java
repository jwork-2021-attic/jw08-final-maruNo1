package com.anish.screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import com.anish.calabashbros.Background;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.Direction;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Fog;
import com.anish.calabashbros.Heart;
import com.anish.calabashbros.Monster;
import com.anish.calabashbros.Star;
import com.anish.calabashbros.Type;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.World;

import asciiPanel.AsciiPanel; 

public class WorldScreen implements Screen {

    public static int WIN_POINT = 5;

    public World world;
    public Calabash bro;
    public Monster monster;
    public Heart heart;
    public Star star;
    public Fog fog;

    public boolean victory = false;


    public WorldScreen() {
        world = new World();

        bro = new Calabash(world);

        fog = new Fog(world);

        world.put(bro, 3, 3);

        new Thread(monster).start();

        new Thread(new MonsPlant()).start();

        new Thread(new StarPlant()).start();

        new Thread(new HeartPlant()).start();

    }

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

    }

    private class MonsPlant implements Runnable{
        @Override
        public void run() {
            while(world.Score<=WIN_POINT){
                try{
                    Thread.sleep(5000); 
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
            while(world.Score<=WIN_POINT){
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
            while(world.Score<=WIN_POINT){
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
                int absx = x - bro.getX();
                int absy = y - bro.getY();
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
        displayMessages2(terminal);
    }

    public void displayMessages(AsciiPanel terminal) {
       
        String s = Integer.toString(bro.lives);

        terminal.write("hp:", World.WIDTH + 1, 3);

        terminal.write( s , World.WIDTH + 7 , 3);
    
    }

    public void displayMessages2(AsciiPanel terminal) {
       
        String s2 = Integer.toString(world.Score);

        terminal.write("score:", World.WIDTH + 1, 6);

        terminal.write( s2 , World.WIDTH + 7 , 6);
    
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
                    bro.moveTo(Direction.UP);
                    break;
                case KeyEvent.VK_A:
                    bro.moveTo(Direction.LEFT);
                    break;
                case KeyEvent.VK_S:
                    bro.moveTo(Direction.DOWN);
                    break;
                case KeyEvent.VK_D:
                    bro.moveTo(Direction.RIGHT);
                    break;
                case KeyEvent.VK_J:
                    bro.shoot();
                    break;
                case KeyEvent.VK_P:
                    world.save();
                    return new PauseScreen();
            }
        }
        else if(world.WinOrLose == 2)
        {
            return new WinScreen();
        }
        return this;
    }

}
