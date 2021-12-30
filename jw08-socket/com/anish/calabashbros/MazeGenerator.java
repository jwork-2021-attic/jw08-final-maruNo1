package com.anish.calabashbros;

public class MazeGenerator {

    public int[][] maze;

    MazeGenerator(int width, int height) {
        maze = new int[width][height];
    }

    public void generateMaze() {
        for(int i=0;i< maze.length;i++)
        {
            for(int j=0;j<maze[0].length;j++)
            {
                if(i==0 || j==0 || i==maze.length-1 || j==maze[0].length-1)
                {
                    maze[i][j] = 1;
                }
                else
                {
                    maze[i][j] = 0;
                }
            }
        }
    }

}
