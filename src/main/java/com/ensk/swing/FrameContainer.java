package com.ensk.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
                renameFolderFiles(folder);
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

        panel.add(clearIconCacheBtn);
        panel.add(removeOneDriveIconBtn);
        panel.add(renameFilesFolderTextField);
        panel.add(renameFilesBtn);

        return panel;
    }

    private static void renameFolderFiles(File folder) {
        File[] files = folder.listFiles();
        int index = 0;
        for (File file : files) {
            index++;
            long lastModified = file.lastModified();
            Date date = new Date(lastModified);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String modifiedDate = dateFormat.format(date);
            File newFile = new File(file.getParent() + File.separator + modifiedDate + "."
                    + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase());
            // If the new file exists and is not the origin file, then add a sequence number after the new name
            if (newFile.exists() && !(newFile.getName().equals(file.getName()))) {
                newFile = new File(file.getParent() + File.separator + modifiedDate + index + "."
                        + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase());
            }
            boolean success = file.renameTo(newFile);
            System.out.println("[" + success + "] " + file + " >>>>> " + newFile);
        }
        JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

}