package com.anish.calabashbros;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public class CalabashTest {
    @Test
    public void testMeetHeart() {
        World world = new World();
        Calabash calabash = new Calabash(world);
        int a = calabash.lives;
        calabash.MeetHeart(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor()); 
        assertEquals(AsciiPanel.brightBlue, world.get(1, 1+calabash.speed).getColor());
        assertEquals(a+1,calabash.lives);
    }

    @Test
    public void testMeetStar() {
        World world = new World();
        Calabash calabash = new Calabash(world);
        calabash.MeetStar(1, 1, Direction.DOWN);
        assertEquals(Color.gray, world.get(1, 1).getColor());
        assertEquals(AsciiPanel.brightBlue, world.get(1, 1+calabash.speed).getColor()); 
        assertTrue(calabash.starred);
        assertEquals(5, calabash.startimes);
    }
}
