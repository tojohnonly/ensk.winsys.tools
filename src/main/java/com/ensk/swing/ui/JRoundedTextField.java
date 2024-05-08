package com.ensk.swing.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.JTextField;

public class JRoundedTextField extends JTextField {
    private static final long serialVersionUID = -1946802815417758252L;

    public JRoundedTextField() {
        super();
        setMargin(new Insets(0, 7, 0, 7));
    }

    public JRoundedTextField(String text) {
        super(text);
        setMargin(new Insets(0, 7, 0, 7));
    }

    @Override
    protected void paintBorder(Graphics g) {
        int h = getHeight();
        int w = getWidth();
        Graphics2D g2d = (Graphics2D)g.create();
        Shape shape = g2d.getClip();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(shape);
        g2d.setColor(Color.GRAY);
        g2d.drawRoundRect(1, 1, w - 2, h - 2, 10, 10);
        g2d.dispose();
        super.paintBorder(g2d);
    }

}