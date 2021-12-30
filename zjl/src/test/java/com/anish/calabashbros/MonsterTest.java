package com.anish.calabashbros;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.awt.Color;
import asciiPanel.AsciiPanel;

public class MonsterTest {

    @Test
    public void testMeetHeart() {
        World world = new World();
        Monster monster = new Monster(world);
        int a = monster.lives;
        monster.MeetHeart(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor()); 
        assertEquals(AsciiPanel.brightCyan, world.get(1, 1+monster.speed).getColor());
        assertEquals(a+1,monster.lives);
    }

    @Test
    public void testMeetStar() {
        World world = new World();
        Monster monster = new Monster(world);
        monster.MeetStar(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor());
        assertEquals(AsciiPanel.brightCyan, world.get(1, 1+monster.speed).getColor()); 

    }
}
