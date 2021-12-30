package com.anish.calabashbros;

import asciiPanel.AsciiPanel;

public class BigBullet extends Creature  implements Runnable{

    public BigBullet(World world) {
        super(AsciiPanel.brightGreen, (char) 249, world );
    }

    @Override
    public void MeetWall(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                this.world.put(new Floor(world), x, y + speed);
                break;
            case LEFT:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                this.world.put(new Floor(world), x - speed, y);
                break;
            case RIGHT:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                this.world.put(new Floor(world), x + speed, y);
                break;
            case UP:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                this.world.put(new Floor(world), x, y - speed);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void MeetBackground(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                break;
            case LEFT:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                break;
            case RIGHT:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                break;
            case UP:
                this.live = false;
                this.world.put(new Floor(world), x, y);
                break;
            default:
                break;
            
        }
    }


    @Override
    public void MeetMonster(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                System.out.println("monster's lives");
                System.out.println(this.world.get(x, y + speed).lives);
                this.world.get(x, y + speed).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x, y + speed).lives <= 0)
                {
                    this.world.get(x, y + speed).live = false;
                }
                if(this.world.get(x, y + speed).live == false)
                {
                    this.world.put(new Floor(world), x, y + speed);
                    System.out.println("Hit!");
                }
                break;
            case LEFT:
                System.out.println("monster's lives");
                System.out.println(this.world.get(x - speed, y).lives);
                this.world.get(x - speed, y).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x - speed, y).lives <= 0)
                {
                    this.world.get(x - speed, y).live = false;
                }
                if(this.world.get(x- speed, y).live == false)
                {
                    this.world.put(new Floor(world), x- speed, y);
                    System.out.println("Hit!");
                }
                break;
            case RIGHT:
                System.out.println("monster's lives");
                System.out.println(this.world.get(x + speed, y).lives);
                this.world.get(x + speed, y).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x + speed, y).lives <= 0)
                {
                    this.world.get(x + speed, y).live = false;
                }
                if(this.world.get(x + speed, y).live == false)
                {
                    this.world.put(new Floor(world), x + speed, y);
                    System.out.println("Hit!");
                }
                break;
            case UP:
                System.out.println("monster's lives");
                System.out.println(this.world.get(x, y - speed).lives);
                this.world.get(x, y - speed).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x, y - speed).lives <= 0)
                {
                    this.world.get(x, y - speed).live = false;
                }
                if(this.world.get(x, y - speed).live == false)
                {
                    this.world.put(new Floor(world), x, y - speed);
                    System.out.println("Hit!");
                }
                break;
            default:
                break;
            
        }
    }

    @Override
    public void MeetCala(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                System.out.println("bro's lives");
                System.out.println(this.world.get(x, y + speed).lives);
                this.world.get(x, y + speed).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x, y + speed).lives <= 0)
                {
                    this.world.get(x, y + speed).live = false;
                }
                if(this.world.get(x, y + speed).live == false)
                {
                    this.world.put(new Floor(world), x, y + speed);
                    System.out.println("Kill!");
                }
                break;
            case LEFT:
                System.out.println("bro's lives");
                System.out.println(this.world.get(x - speed, y).lives);
                this.world.get(x - speed, y).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x - speed, y).lives <= 0)
                {
                    this.world.get(x - speed, y).live = false;
                }
                if(this.world.get(x- speed, y).live == false)
                {
                    this.world.put(new Floor(world), x- speed, y);
                    System.out.println("Kill!");
                }
                break;
            case RIGHT:
                System.out.println("bro's lives");
                System.out.println(this.world.get(x + speed, y).lives);
                this.world.get(x + speed, y).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x + speed, y).lives <= 0)
                {
                    this.world.get(x + speed, y).live = false;
                }
                if(this.world.get(x + speed, y).live == false)
                {
                    this.world.put(new Floor(world), x + speed, y);
                    System.out.println("Kill!");
                }
                break;
            case UP:
                System.out.println("bro's lives");
                System.out.println(this.world.get(x, y - speed).lives);
                this.world.get(x, y - speed).lives-=2;
                this.live = false;
                this.world.put(new Floor(world), x, y);
                if(this.world.get(x, y - speed).lives <= 0)
                {
                    this.world.get(x, y - speed).live = false;
                }
                if(this.world.get(x, y - speed).live == false)
                {
                    this.world.put(new Floor(world), x, y - speed);
                    System.out.println("Kill!");
                }
                break;
            default:
                break;
            
        }
    }


    @Override
    public void MeetHeart(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                this.world.put(new Floor(world), x, y + speed);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case LEFT:
                this.world.put(new Floor(world), x - speed, y);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case RIGHT:
                this.world.put(new Floor(world), x + speed, y);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case UP:
                this.world.put(new Floor(world), x, y - speed);
                this.world.put(new Floor(world), x, y);
                this.live = false;
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
                this.world.put(new Floor(world), x, y + speed);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case LEFT:
                this.world.put(new Floor(world), x - speed, y);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case RIGHT:
                this.world.put(new Floor(world), x + speed, y);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            case UP:
                this.world.put(new Floor(world), x, y - speed);
                this.world.put(new Floor(world), x, y);
                this.live = false;
                break;
            default:
                break;
        }
    }


    @Override
    public void run() {
        while(this.live) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
            this.moveTo(this.direction);


        }
    }

}
