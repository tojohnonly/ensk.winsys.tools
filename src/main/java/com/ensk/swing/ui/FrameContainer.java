package com.ensk.swing.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ensk.swing.config.WtsConfig;
import com.ensk.swing.service.ClearIconCacheService;
import com.ensk.swing.service.RemoveOneDriveIconService;
import com.ensk.swing.service.RenameFileService;

public class FrameContainer {

    static final JFrame frame = new JFrame("Ensk's Tools");
    static JPanel modePanel = assembleModePanel();

    public static JFrame assembleFrame() {
        frame.setSize(460, 310);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(WtsConfig.bgColor);
        URL iconURL = FrameContainer.class.getResource("/appicon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());
        frame.add(modePanel);
        return frame;
    }

    public static JPanel assembleModePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(WtsConfig.bgColor);

        /** Clear Icon Cache Button */
        final JRoundedButton clearIconCacheBtn = new JRoundedButton("Clear Icon Cache");
        clearIconCacheBtn.setBounds(50, 30, 345, 35);
        clearIconCacheBtn.setForeground(Color.WHITE);
        clearIconCacheBtn.setFont(WtsConfig.buttonFont);
        clearIconCacheBtn.setBackground(WtsConfig.buttonBgColor);
        clearIconCacheBtn.setBorderPainted(false);
        clearIconCacheBtn.setFocusPainted(false);
        clearIconCacheBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ClearIconCacheService.clearIconCache();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                clearIconCacheBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                clearIconCacheBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });

        /** Remove OneDrive Icon Button */
        final JRoundedButton removeOneDriveIconBtn = new JRoundedButton("Remove OneDrive Icon");
        removeOneDriveIconBtn.setBounds(50, 80, 345, 35);
        removeOneDriveIconBtn.setForeground(Color.WHITE);
        removeOneDriveIconBtn.setFont(WtsConfig.buttonFont);
        removeOneDriveIconBtn.setBackground(WtsConfig.buttonBgColor);
        removeOneDriveIconBtn.setBorderPainted(false);
        removeOneDriveIconBtn.setFocusPainted(false);
        removeOneDriveIconBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                RemoveOneDriveIconService.removeOneDriveIcon();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                removeOneDriveIconBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                removeOneDriveIconBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });

        /** Rename Files Folder Text Field */
        JRoundedTextField renameFilesFolderTextField = new JRoundedTextField();
        renameFilesFolderTextField.setBounds(52, 130, 341, 35);
        renameFilesFolderTextField.setForeground(Color.WHITE);
        renameFilesFolderTextField.setCaretColor(Color.WHITE);
        renameFilesFolderTextField.setFont(WtsConfig.buttonFont);
        renameFilesFolderTextField.setBackground(WtsConfig.bgColor);
        /** Rename Files Text Field */
        final JRoundedButton renameFilesBtn = new JRoundedButton("Rename Files in Folder Above");
        renameFilesBtn.setBounds(50, 175, 345, 35);
        renameFilesBtn.setForeground(Color.WHITE);
        renameFilesBtn.setFont(WtsConfig.buttonFont);
        renameFilesBtn.setBackground(WtsConfig.buttonBgColor);
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
                renameFilesBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                renameFilesBtn.setBackground(WtsConfig.buttonBgColor);
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