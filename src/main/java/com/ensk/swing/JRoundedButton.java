package com.ensk.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.border.Border;

public class JRoundedButton extends JButton {
    public JRoundedButton(String s) {
        super(s);
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(new RoundBorder());
        setContentAreaFilled(false);
        // setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            // UIManager.put("Button.select", new Color(118, 116, 115));
            g2d.setColor(new Color(118, 116, 115));
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
        super.paintComponent(g);
    }

}

class RoundBorder implements Border {
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 10, 10);
    }
}