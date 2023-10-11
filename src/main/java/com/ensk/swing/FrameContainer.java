package com.ensk.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameContainer {

    static Font buttonFont = new Font("Microsoft YaHei UI", Font.PLAIN, 15);
    static Color panelBgColor = new Color(41, 41, 41);
    static Color buttonBgColor = new Color(70, 70, 70);
    static Color buttonHoverColor = new Color(96, 96, 96);
    static final JFrame frame = new JFrame("Ensk's Tools");
    static JPanel modePanel = assembleModePanel();

    public static JFrame assembleFrame() {
        frame.setSize(460, 310);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(panelBgColor);

        URL iconURL = FrameContainer.class.getResource("/appicon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());

        frame.add(modePanel);
        return frame;
    }

    public static JPanel assembleModePanel() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(panelBgColor);

        final JRoundedButton clearIconCacheBtn = new JRoundedButton("Clear Icon Cache");
        clearIconCacheBtn.setBounds(50, 30, 345, 35);
        clearIconCacheBtn.setForeground(Color.WHITE);
        clearIconCacheBtn.setFont(buttonFont);
        clearIconCacheBtn.setBackground(buttonBgColor);
        clearIconCacheBtn.setBorder(new RoundBorder());
        clearIconCacheBtn.setBorderPainted(false);
        clearIconCacheBtn.setFocusPainted(false);
        clearIconCacheBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                File userHome = new File(System.getProperty("user.home"));
                File iconCacheFile = new File(userHome, "AppData\\Local\\iconcache.db");
                if (iconCacheFile.exists()) {
                    iconCacheFile.delete();
                }

                try {
                    Runtime.getRuntime().exec("cmd /c taskkill /f /im explorer.exe");
                    Thread.sleep(2000);
                    Runtime.getRuntime().exec("cmd /c start explorer.exe");
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                clearIconCacheBtn.setBackground(buttonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                clearIconCacheBtn.setBackground(buttonBgColor);
            }
        });

        final JRoundedButton removeOneDriveIconBtn = new JRoundedButton("Remove OneDrive Icon");
        removeOneDriveIconBtn.setBounds(50, 80, 345, 35);
        removeOneDriveIconBtn.setForeground(Color.WHITE);
        removeOneDriveIconBtn.setFont(buttonFont);
        removeOneDriveIconBtn.setBackground(buttonBgColor);
        removeOneDriveIconBtn.setBorder(new RoundBorder());
        removeOneDriveIconBtn.setBorderPainted(false);
        removeOneDriveIconBtn.setFocusPainted(false);
        removeOneDriveIconBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd /c reg add HKEY_CLASSES_ROOT\\CLSID\\{018D5C66-4533-4307-9B53-224DE2ED1FE6} " +
                            "/v System.IsPinnedToNameSpaceTree /t REG_DWORD /d 0 /f");
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                removeOneDriveIconBtn.setBackground(buttonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                removeOneDriveIconBtn.setBackground(buttonBgColor);
            }
        });

        final JRoundedButton mixedModeBtn = new JRoundedButton("Coming Soon...");
        mixedModeBtn.setBounds(50, 130, 345, 35);
        mixedModeBtn.setForeground(Color.WHITE);
        mixedModeBtn.setFont(buttonFont);
        mixedModeBtn.setBackground(buttonBgColor);
        mixedModeBtn.setBorder(new RoundBorder());
        mixedModeBtn.setBorderPainted(false);
        mixedModeBtn.setFocusPainted(false);
        mixedModeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    Thread.sleep(1000);
                    mixedModeBtn.setText("Done!");
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                frame.validate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mixedModeBtn.setBackground(buttonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mixedModeBtn.setBackground(buttonBgColor);
            }
        });

        panel.add(clearIconCacheBtn);
        panel.add(removeOneDriveIconBtn);
        panel.add(mixedModeBtn);
        return panel;
    }

}