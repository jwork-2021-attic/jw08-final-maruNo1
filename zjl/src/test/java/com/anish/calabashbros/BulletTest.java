package com.anish.calabashbros;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.awt.Color;
import org.junit.Test;

import asciiPanel.AsciiPanel;

public class BulletTest {
    @Test
    public void testMeetBackground() {
        World world = new World();
        Bullet bullet = new Bullet(world);
        bullet.MeetBackground(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor()); 
        assertFalse(bullet.live);
    }

    @Test
    public void testMeetHeart() {
        World world = new World();
        Bullet bullet = new Bullet(world);
        bullet.MeetHeart(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor()); 
        assertEquals(Color.gray, world.get(1, 1+bullet.speed).getColor());
        assertFalse(bullet.live);
    }

    @Test
    public void testMeetMonster() {
        World world = new World();
        Bullet bullet = new Bullet(world);
        Monster monster = new Monster(world);
        world.put(monster, 1, 2);
        int x = monster.lives;
        bullet.MeetMonster(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor()); 
        assertEquals(AsciiPanel.brightCyan, world.get(1, 1+bullet.speed).getColor());
        assertEquals(x-1, monster.lives);
        assertFalse(bullet.live);
    }

    @Test
    public void testMeetStar() {
        World world = new World();
        Bullet bullet = new Bullet(world);
        bullet.MeetStar(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor()); 
        assertEquals(Color.gray, world.get(1, 1+bullet.speed).getColor());
        assertFalse(bullet.live);
    }

    @Test
    public void testMeetWall() {
        World world = new World();
        Bullet bullet = new Bullet(world);
        bullet.MeetWall(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor());
        assertFalse(bullet.live);
    }

}
