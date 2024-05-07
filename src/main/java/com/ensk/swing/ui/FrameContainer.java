package com.ensk.swing.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ensk.swing.service.ClearIconCacheService;
import com.ensk.swing.service.RemoveOneDriveIconService;
import com.ensk.swing.service.RenameFileService;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class FrameContainer {

    static Color panelBgColor = getSystemColor();
    static Font buttonFont = new Font("Microsoft YaHei UI", Font.PLAIN, 15);
    static Color buttonBgColor = generateButtonBgColor(panelBgColor);
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

    public static Color getSystemColor() {
        long color = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\History\\Colors", "ColorHistory0");
        int b = (int) ((color >> 16) & 0xFF);
        int g = (int) ((color >> 8) & 0xFF);
        int r = (int) (color & 0xFF);
        return new Color(r, g, b);
    }

    public static Color generateButtonBgColor(Color panelBgColor) {
        int r = (panelBgColor.getRed() + 30) > 255 ? 255 : panelBgColor.getRed() + 25;
        int g = (panelBgColor.getGreen() + 30) > 255 ? 255 : panelBgColor.getGreen() + 25;
        int b = (panelBgColor.getBlue() + 30) > 255 ? 255 : panelBgColor.getBlue() + 25;
        return new Color(r, g, b);
    }

    public static JPanel assembleModePanel() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(panelBgColor);

        /** Clear Icon Cache Button */
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
                ClearIconCacheService.clearIconCache();
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

        /** Remove OneDrive Icon Button */
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
                RemoveOneDriveIconService.removeOneDriveIcon();
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

        /** Rename Files Folder Text Field */
        JTextField renameFilesFolderTextField = new JTextField();
        renameFilesFolderTextField.setBounds(52, 130, 341, 35);
        renameFilesFolderTextField.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
        renameFilesFolderTextField.setForeground(Color.WHITE);
        renameFilesFolderTextField.setFont(buttonFont);
        renameFilesFolderTextField.setBackground(buttonBgColor);
        /** Rename Files Text Field */
        final JRoundedButton renameFilesBtn = new JRoundedButton("Rename Files in Folder Above");
        renameFilesBtn.setBounds(50, 175, 345, 35);
        renameFilesBtn.setForeground(Color.WHITE);
        renameFilesBtn.setFont(buttonFont);
        renameFilesBtn.setBackground(buttonBgColor);
        renameFilesBtn.setBorder(new RoundBorder());
        renameFilesBtn.setBorderPainted(false);
        renameFilesBtn.setFocusPainted(false);
        renameFilesBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = renameFilesFolderTextField.getText().trim();
                if (0 == folderStr.length()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    renameFilesFolderTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists() || !folder.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    renameFilesFolderTextField.grabFocus();
                    return;
                }
                RenameFileService.renameFolderFiles(folder);
                JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                renameFilesFolderTextField.setText("");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                renameFilesBtn.setBackground(buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                renameFilesBtn.setBackground(buttonBgColor);
            }
        });

        /** Assemble Mode Panel */
        panel.add(clearIconCacheBtn);
        panel.add(removeOneDriveIconBtn);
        panel.add(renameFilesFolderTextField);
        panel.add(renameFilesBtn);
        return panel;
    }

}