package com.anish.calabashbros;

import java.awt.Color;

public class Creature extends Thing {

    public int speed = 1;
    public Direction direction;
    public boolean starred = false;
    public int startimes = 0;

    Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
    }

    public void MeetCala(int x, int y, Direction direction)
    {
        
    }

    public void MeetFloor(int x, int y, Direction direction)
    {
        switch(direction) {
            case DOWN:
                this.world.put(this, x, y + speed);
                this.world.put(new Floor(world), x, y);
                break;
            case LEFT:
                this.world.put(this, x - speed, y);
                this.world.put(new Floor(world), x, y);
                break;
            case RIGHT:
                this.world.put(this, x + speed, y);
                this.world.put(new Floor(world), x, y);
                break;
            case UP:
                this.world.put(this, x, y - speed);
                this.world.put(new Floor(world), x, y);
                break;
            default:
                break;
        }
    }

    public void MeetWall(int x, int y, Direction direction)
    {

    }

    public void MeetHeart(int x, int y, Direction direction)
    {

    }

    public void MeetBackground(int x, int y, Direction direction)
    {

    }

    public void MeetBigBullet(int x, int y, Direction direction)
    {

    }

    public void MeetBullet(int x, int y, Direction direction) 
    {

    }

    public void MeetStar(int x, int y, Direction direction)
    {

    }

    public void MeetMonster(int x, int y, Direction direction)
    {

    }

    public void moveTo(Direction direction) {
        this.direction = direction;
        int x = this.getX();
        int y = this.getY();
        switch(direction) {
            case UP:
                if(this.world.checkType(x, y - speed) == Type.FLOOR)
                {
                    MeetFloor(x,y,Direction.UP);
                }
                else if(this.world.checkType(x, y - speed) == Type.HEART)
                {
                    MeetHeart(x, y, direction);
                }
                else if(this.world.checkType(x, y - speed) == Type.MONSTER)
                {
                    MeetMonster(x, y, direction);
                }
                else if(this.world.checkType(x, y - speed) == Type.STAR)
                {
                    MeetStar(x, y, direction);
                }
                else if(this.world.checkType(x, y - speed) == Type.WALL)
                {
                    MeetWall(x, y, Direction.UP);
                }
                else if(this.world.checkType(x, y - speed) == Type.BACKGROUND)
                {
                    MeetBackground(x, y, direction);
                }
                else if(this.world.checkType(x, y - speed) == Type.BULLET)
                {
                    MeetBullet(x, y, direction);
                }
                else if(this.world.checkType(x, y - speed) == Type.BIGBULLET)
                {
                    MeetBigBullet(x, y, direction);
                }
                else if(this.world.checkType(x, y - speed) == Type.CALABASH)
                {
                    MeetCala(x, y, direction);
                }
                break;
            case LEFT:
                if(this.world.checkType(x- speed, y) == Type.FLOOR)
                {
                    MeetFloor(x,y,Direction.LEFT);
                }
                else if(this.world.checkType(x- speed, y) == Type.HEART)
                {
                    MeetHeart(x, y, direction);
                }
                else if(this.world.checkType(x - speed, y) == Type.MONSTER)
                {
                    MeetMonster(x, y, direction);
                }
                else if(this.world.checkType(x - speed, y) == Type.STAR)
                {
                    MeetStar(x, y, direction);
                }
                else if(this.world.checkType(x- speed, y) == Type.WALL)
                {
                    MeetWall(x, y, Direction.LEFT);
                }
                else if(this.world.checkType(x - speed, y) == Type.BACKGROUND)
                {
                    MeetBackground(x, y, direction);
                }
                else if(this.world.checkType(x - speed, y) == Type.BULLET)
                {
                    MeetBullet(x, y, direction);
                }
                else if(this.world.checkType(x - speed, y) == Type.BIGBULLET)
                {
                    MeetBigBullet(x, y, direction);
                }
                else if(this.world.checkType(x - speed, y) == Type.CALABASH)
                {
                    MeetCala(x, y, direction);
                }
                break;
            case DOWN:
                if(this.world.checkType(x, y + speed) == Type.FLOOR)
                {
                    MeetFloor(x,y,Direction.DOWN);
                }
                else if(this.world.checkType(x, y + speed) == Type.HEART)
                {
                    MeetHeart(x, y, direction);
                }
                else if(this.world.checkType(x, y + speed) == Type.MONSTER)
                {
                    MeetMonster(x, y, direction);
                }
                else if(this.world.checkType(x, y + speed) == Type.STAR)
                {
                    MeetStar(x, y, direction);
                }
                else if(this.world.checkType(x, y + speed) == Type.WALL)
                {
                    MeetWall(x, y, Direction.DOWN);
                }
                else if(this.world.checkType(x, y + speed) == Type.BACKGROUND)
                {
                    MeetBackground(x, y, direction);
                }
                else if(this.world.checkType(x, y + speed) == Type.BULLET)
                {
                    MeetBullet(x, y, direction);
                }
                else if(this.world.checkType(x, y + speed) == Type.BIGBULLET)
                {
                    MeetBigBullet(x, y, direction);
                }
                else if(this.world.checkType(x, y + speed) == Type.CALABASH)
                {
                    MeetCala(x, y, direction);
                }
                break;
            case RIGHT:
                if(this.world.checkType(x + speed, y) == Type.FLOOR)
                {
                    MeetFloor(x,y,Direction.RIGHT);
                }
                else if(this.world.checkType(x + speed, y) == Type.HEART)
                {
                    MeetHeart(x, y, direction);
                }
                else if(this.world.checkType(x + speed, y) == Type.MONSTER)
                {
                    MeetMonster(x, y, direction);
                }
                else if(this.world.checkType(x + speed, y) == Type.STAR)
                {
                    MeetStar(x, y, direction);
                }
                else if(this.world.checkType(x + speed, y) == Type.WALL)
                {
                    MeetWall(x, y, Direction.RIGHT);
                }
                else if(this.world.checkType(x + speed, y) == Type.BACKGROUND)
                {
                    MeetBackground(x, y, direction);
                }
                else if(this.world.checkType(x + speed, y) == Type.BULLET)
                {
                    MeetBullet(x, y, direction);
                }
                else if(this.world.checkType(x + speed, y) == Type.BIGBULLET)
                {
                    MeetBigBullet(x, y, direction);
                }
                else if(this.world.checkType(x + speed, y) == Type.CALABASH)
                {
                    MeetCala(x, y, direction);
                }
                break;
            default:
                break;
        }
        
    }

}
