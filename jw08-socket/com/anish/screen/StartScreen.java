package com.anish.screen;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("start!.", 0, 0);
        terminal.write("Press ENTER!", 0, 1);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new WorldScreen();
            default:
                return this;
        }
    }

    @Override
    public void action(String string, String string2) {
        // TODO Auto-generated method stub
        
    }

}
