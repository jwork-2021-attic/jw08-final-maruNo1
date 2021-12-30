package com.anish.screen;

import asciiPanel.AsciiPanel;

public class LoseScreen extends RestartScreen {
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Lose!", 0, 0);
        
    }

    @Override
    public void action(String string, String string2) {
        // TODO Auto-generated method stub
        
    }
    
}
