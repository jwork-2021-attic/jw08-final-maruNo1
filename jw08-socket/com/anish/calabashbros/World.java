package com.anish.calabashbros;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import asciiPanel.AsciiPanel;
public class World {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private MazeGenerator mazeGenerator;

    private Tile<Thing>[][] tiles;

    public Integer[][] map;

    
    public int Score = 0;

    public int WinOrLose = 1;

    public World() {

        if (tiles == null) {
            tiles = new Tile [WIDTH][HEIGHT];
        }
        if (map == null) {
            map = new Integer[WIDTH][HEIGHT];
        }

        mazeGenerator = new MazeGenerator(WIDTH, HEIGHT);

        mazeGenerator.generateMaze();

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                map[i][j] = mazeGenerator.maze[i][j];
            }
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                if(mazeGenerator.maze[i][j] == 0)
                {
                    tiles[i][j].setThing(new Floor(this));
                }
                else if(mazeGenerator.maze[i][j] == 1)
                {
                    tiles[i][j].setThing(new Background(this));
                }
                else if(mazeGenerator.maze[i][j] == 2)
                {
                    tiles[i][j].setThing(new Wall(this));
                }
            }
        }
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public Type checkType(int x,int y) {
        if(tiles[x][y].getThing().getColor() == Color.gray && tiles[x][y].getThing().getGlyph() == 0 )
        {
            return Type.FLOOR;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.cyan && tiles[x][y].getThing().getGlyph() == 177 )
        {
            return Type.WALL;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightCyan && tiles[x][y].getThing().getGlyph() == 3)
        {
            return Type.MONSTER;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightBlue && tiles[x][y].getThing().getGlyph() == 2)
        {
            return Type.CALABASH;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightYellow && tiles[x][y].getThing().getGlyph() == 15)
        {
            return Type.STAR;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightRed && tiles[x][y].getThing().getGlyph() == 6)
        {
            return Type.HEART;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightMagenta && tiles[x][y].getThing().getGlyph() == 155)
        {
            return Type.BACKGROUND;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightGreen && tiles[x][y].getThing().getGlyph() == 250)
        {
            return Type.BULLET;
        }
        else if(tiles[x][y].getThing().getColor() == AsciiPanel.brightGreen && tiles[x][y].getThing().getGlyph() == 249)
        {
            return Type.BIGBULLET;
        }
        return null;
    }

    public void save() throws IOException {

        for(int i=0;i<World.WIDTH;i++)
        {
            for(int j=0;j<World.HEIGHT;j++)
            {
                if(this.checkType(i,j) == Type.FLOOR)
                {
                    map[i][j]=0;
                }
                else if(this.checkType(i,j) == Type.BACKGROUND)
                {
                    map[i][j]=1;
                }
                else if(this.checkType(i,j) == Type.WALL)
                {
                    map[i][j]=2;
                }
                else if(this.checkType(i,j) == Type.MONSTER)
                {
                    map[i][j]=3;
                }
                else if(this.checkType(i,j) == Type.CALABASH)
                {
                    map[i][j]=4;
                }
                else if(this.checkType(i,j) == Type.STAR)
                {
                    map[i][j]=5;
                }
                else if(this.checkType(i,j) == Type.HEART)
                {
                    map[i][j]=6;
                }
                else if(this.checkType(i,j) == Type.BULLET)
                {
                    map[i][j]=7;
                }
                else if(this.checkType(i,j) == Type.BIGBULLET)
                {
                    map[i][j]=8;
                }
            }
        }
        /*
        File file = new File("D:\\jw05-gamev2.0\\resources\\map.txt");
        FileWriter out = new FileWriter(file);
        for(int i=0;i<WIDTH;i++)
        {
            for(int j=0;j<HEIGHT;j++)
            {
                out.write(map[i][j]+"\t");
            }
            out.write("\r\n");
        }
        out.close();*/

    }

    public void load() {
        File file = new File("D:\\jw05-gamev2.0\\resources\\map.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) 
        {
            String line;  //一行数据
            int row=0;
            //逐行读取，并将每个数组放入到数组中
            while((line = in.readLine()) != null)
            {
                String[] temp = line.split("\t"); 
                for(int j=0;j<temp.length;j++)
                {
                    map[row][j] = (int) Double.parseDouble(temp[j]);
                }
                row++;
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        /*
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                if(map[i][j] == 0)
                {
                    tiles[i][j].setThing(new Floor(this));
                }
                else if(map[i][j] == 1)
                {
                    tiles[i][j].setThing(new Background(this));
                }
                else if(map[i][j] == 2)
                {
                    tiles[i][j].setThing(new Wall(this));
                }
                else if(map[i][j] == 3)
                {
                    tiles[i][j].setThing(new Monster(this));
                }
                else if(map[i][j] == 4)
                {
                    tiles[i][j].setThing(new Calabash(this));
                }
                else if(map[i][j] == 5)
                {
                    tiles[i][j].setThing(new Star(this));
                }
                else if(map[i][j] == 6)
                {
                    tiles[i][j].setThing(new Heart(this));
                }
            }
        }*/
         
        
    }
    

}
