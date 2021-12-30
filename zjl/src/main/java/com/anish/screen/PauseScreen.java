package com.anish.screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class PauseScreen extends RestartScreen {
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Lose!", 0, 0);
        
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_P:
                return new WorldScreen(true);
            default:
                return this;
        }
    }
    
}