package com.anish.screen;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("start!.", 0, 0);
        terminal.write("Press ENTER To Start Game!", 0, 1);
        terminal.write("Press L To Load Map!", 0, 2);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new WorldScreen(); 
            case KeyEvent.VK_L:
                return new WorldScreen(true);
            default:
                return this;
        }
    }

}
