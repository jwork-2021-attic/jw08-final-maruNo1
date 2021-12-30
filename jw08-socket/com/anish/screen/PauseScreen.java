package com.anish.screen;

import asciiPanel.AsciiPanel;

public class PauseScreen extends RestartScreen {
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Pause!", 0, 0);
        
    }

    @Override
    public void action(String string, String string2) {
        // TODO Auto-generated method stub
        
    }
    
}
