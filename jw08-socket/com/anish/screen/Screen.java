package com.anish.screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key) throws IOException;

    public void action(String string, String string2) throws IOException;
    
}
