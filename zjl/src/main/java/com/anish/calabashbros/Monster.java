package com.anish.calabashbros;

import com.anish.screen.WorldScreen;

import asciiPanel.AsciiPanel;

public class Monster extends Creature implements Runnable{

    public Monster(World world) {
        super(AsciiPanel.brightCyan, (char)3, world);
    }

    @Override
    public void MeetCala(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                if(this.world.get(x, y + speed).lives>0)
                    this.world.get(x, y + speed).lives--;
                break;
            case LEFT:
                if(this.world.get(x - speed, y).lives>0)
                    this.world.get(x - speed, y).lives--;
                break;
            case RIGHT:
                if(this.world.get(x + speed, y).lives>0)
                    this.world.get(x + speed, y).lives--;
                break;
            case UP:
                if(this.world.get(x, y - speed).lives>0)
                    this.world.get(x, y - speed).lives--;
                break;
            default:
                break;
        }
        if(this.world.get(x, y - speed).lives<=0)
        {
            this.world.WinOrLose = 0;
        }
    }

    @Override
    public void MeetHeart(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                this.lives++;
                System.out.println("this.lives=");
                System.out.println(this.lives);
                this.world.put(this, x, y + speed);
                this.world.put(new Floor(world), x, y);
                break;
            case LEFT:
                this.lives++;
                System.out.println("this.lives=");
                System.out.println(this.lives);
                this.world.put(this, x- speed, y);
                this.world.put(new Floor(world), x, y);
                break;
            case RIGHT:
                this.lives++;
                System.out.println("this.lives=");
                System.out.println(this.lives);
                this.world.put(this, x + speed, y);
                this.world.put(new Floor(world), x, y);
                break;
            case UP:
                this.lives++;
                System.out.println("this.lives=");
                System.out.println(this.lives);
                this.world.put(this, x, y - speed);
                this.world.put(new Floor(world), x, y);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void MeetStar(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                this.world.put(this, x, y + speed);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case LEFT:
                this.world.put(this, x - speed, y);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case RIGHT:
                this.world.put(this, x + speed, y);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case UP:
                this.world.put(this, x, y - speed);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            default:
                break;
        }
    }


    public Direction FindWay()
    {
        int destx = this.getX();
        int desty = this.getY();
        for(int i=0;i<World.WIDTH;i++)
        {
            for(int j=0;j<World.HEIGHT;j++)
            {
                if(this.world.checkType(i, j) == Type.CALABASH)
                {
                    destx = i;
                    desty = j;
                }
            }
        }
        int[][] map1 = new  int[World.WIDTH][World.HEIGHT];
        for(int i=0;i<World.WIDTH;i++)
        {
            for(int j=0;j<World.HEIGHT;j++)
            {
                map1[i][j] = world.map[i][j] ;
            }
        }
        Maze maze = new Maze(map1, this.getX(), this.getY(), destx, desty);
		maze.bfs();
        int m = maze.stepX - this.getX();
        int n = maze.stepY - this.getY();
        if(m==1 && n==0)
        {
            return Direction.RIGHT;
        }
        else if(m==0 && n==1)
        {
            return Direction.DOWN;
        }
        else if(m==-1 && n==0)
        {
            return Direction.LEFT;
        }
        else if(m==0 && n==-1)
        {
            return Direction.UP;
        }
        return Direction.UP;
    }

    @Override
    public void run() {
        while (this.live == true){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
            this.moveTo(this.FindWay());
        }
        if(this.live == false)
        {
            world.Score+=2;
            this.world.put(new Floor(world), this.getX(), this.getY());
            if(this.world.Score > WorldScreen.WIN_POINT)
            {
                world.WinOrLose = 2;
            }
        }
        
    }

}
