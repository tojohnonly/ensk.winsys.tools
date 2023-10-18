package com.ensk.swing;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Application {

    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        JFrame frame = FrameContainer.assembleFrame();
        frame.setVisible(true);
    }

}