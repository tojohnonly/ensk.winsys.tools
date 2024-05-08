package com.ensk.swing.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class JRoundedButton extends JButton {

    public JRoundedButton(String s) {
        super(s);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2d.setColor(new Color(118, 116, 115));
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillRoundRect(1, 1, getSize().width - 2, getSize().height - 2, 10, 10);
        super.paintComponent(g);
    }

}
