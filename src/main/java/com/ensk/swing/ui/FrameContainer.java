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
        frame.setSize(460, 170);
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

        /** Rename Files Folder Text Field */
        JRoundedTextField renameFilesFolderTextField = new JRoundedTextField();
        renameFilesFolderTextField.setBounds(52, 20, 341, 35);
        renameFilesFolderTextField.setForeground(Color.WHITE);
        renameFilesFolderTextField.setCaretColor(Color.WHITE);
        renameFilesFolderTextField.setFont(WtsConfig.buttonFont);
        renameFilesFolderTextField.setBackground(WtsConfig.bgColor);
        /** Rename Files Text Field */
        final JRoundedButton renameFilesBtn = new JRoundedButton("Rename Files in Folder Above");
        renameFilesBtn.setBounds(50, 70, 345, 35);
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
        panel.add(renameFilesFolderTextField);
        panel.add(renameFilesBtn);
        return panel;
    }

}