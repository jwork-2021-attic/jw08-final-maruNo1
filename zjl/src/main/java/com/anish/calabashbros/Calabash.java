package com.anish.calabashbros;

import asciiPanel.AsciiPanel;

public class Calabash extends Creature {

    public Calabash(World world) {
        super(AsciiPanel.brightBlue , (char) 2, world);
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
        this.startimes = 5;
        switch(direction) {
            case DOWN:
                this.starred = true;
                System.out.println("Reinforced!");
                this.world.put(this, x, y + speed);
                this.world.put(new Floor(world), x, y);
                break;
            case LEFT:
                this.starred = true;
                System.out.println("Reinforced!");
                this.world.put(this, x- speed, y);
                this.world.put(new Floor(world), x, y);
                break;
            case RIGHT:
                this.starred = true;
                System.out.println("Reinforced!");
                this.world.put(this, x + speed, y);
                this.world.put(new Floor(world), x, y);
                break;
            case UP:
                this.starred = true;
                System.out.println("Reinforced!");
                this.world.put(this, x, y - speed);
                this.world.put(new Floor(world), x, y);
                break;
            default:
                break;
            
        }
    }

    public void shoot() {
        if(this.starred == false)
        {
            Bullet bullet = new Bullet(world);
            bullet.direction = this.direction;
            switch(this.direction) {
                case DOWN:
                    world.put(bullet, this.getX(), this.getY()+1);
                    new Thread(bullet).start();
                    break;
                case LEFT:
                    world.put(bullet, this.getX()-1, this.getY());
                    new Thread(bullet).start();
                    break;
                case RIGHT:
                    world.put(bullet, this.getX()+1, this.getY());
                    new Thread(bullet).start();
                    break;
                case UP:
                    world.put(bullet, this.getX(), this.getY()-1);
                    new Thread(bullet).start();
                    break;
                default:
                    break;
            }
        }
        else if(this.starred == true)
        {
            this.startimes--;
            BigBullet bullet = new BigBullet(world);
            bullet.direction = this.direction;
            switch(this.direction) {
                case DOWN:
                    world.put(bullet, this.getX(), this.getY()+1);
                    new Thread(bullet).start();
                    break;
                case LEFT:
                    world.put(bullet, this.getX()-1, this.getY());
                    new Thread(bullet).start();
                    break;
                case RIGHT:
                    world.put(bullet, this.getX()+1, this.getY());
                    new Thread(bullet).start();
                    break;
                case UP:
                    world.put(bullet, this.getX(), this.getY()-1);
                    new Thread(bullet).start();
                    break;
                default:
                    break;
            }
            if(this.startimes <= 0)
            {
                this.starred = false;
            }
        }
    }

}
