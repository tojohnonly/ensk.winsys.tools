package com.ensk.swing;

import javax.swing.JFrame;

public class Application {

    public static void main(String[] args) {
        JFrame frame = FrameContainer.assembleFrame();
        frame.setVisible(true);
    }

}