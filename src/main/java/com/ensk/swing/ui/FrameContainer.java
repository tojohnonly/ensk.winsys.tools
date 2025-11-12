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
        frame.setSize(460, 450);
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
        JRoundedTextField renameFilesFolderTextField = new JRoundedTextField("folder path");
        renameFilesFolderTextField.setBounds(52, 20, 341, 35);
        renameFilesFolderTextField.setForeground(Color.WHITE);
        renameFilesFolderTextField.setCaretColor(Color.WHITE);
        renameFilesFolderTextField.setFont(WtsConfig.buttonFont);
        renameFilesFolderTextField.setBackground(WtsConfig.bgColor);
        // Rename Files Text Field
        final JRoundedButton renameFilesBtn = new JRoundedButton("Rename Files in Folder Above");
        renameFilesBtn.setBounds(50, 60, 345, 35);
        renameFilesBtn.setForeground(Color.WHITE);
        renameFilesBtn.setFont(WtsConfig.buttonFont);
        renameFilesBtn.setBackground(WtsConfig.buttonBgColor);
        renameFilesBtn.setBorderPainted(false);
        renameFilesBtn.setFocusPainted(false);
        renameFilesBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = renameFilesFolderTextField.getText().trim();
                if (folderStr.length() <= 0) {
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


        /** Prune File */
        JRoundedTextField pruneFileNameTextField = new JRoundedTextField("folder path");
        pruneFileNameTextField.setBounds(52, 120, 341, 35);
        pruneFileNameTextField.setForeground(Color.WHITE);
        pruneFileNameTextField.setCaretColor(Color.WHITE);
        pruneFileNameTextField.setFont(WtsConfig.buttonFont);
        pruneFileNameTextField.setBackground(WtsConfig.bgColor);
        JRoundedTextField pruneFileNameCutFrontTextField = new JRoundedTextField("length");
        pruneFileNameCutFrontTextField.setBounds(52, 160, 81, 35);
        pruneFileNameCutFrontTextField.setForeground(Color.WHITE);
        pruneFileNameCutFrontTextField.setCaretColor(Color.WHITE);
        pruneFileNameCutFrontTextField.setFont(WtsConfig.buttonFont);
        pruneFileNameCutFrontTextField.setBackground(WtsConfig.bgColor);
        JRoundedTextField pruneFileNameCutTailTextField = new JRoundedTextField("length");
        pruneFileNameCutTailTextField.setBounds(138, 160, 81, 35);
        pruneFileNameCutTailTextField.setForeground(Color.WHITE);
        pruneFileNameCutTailTextField.setCaretColor(Color.WHITE);
        pruneFileNameCutTailTextField.setFont(WtsConfig.buttonFont);
        pruneFileNameCutTailTextField.setBackground(WtsConfig.bgColor);
        JRoundedTextField pruneFileNameAddFrontTextField = new JRoundedTextField("prefix");
        pruneFileNameAddFrontTextField.setBounds(224, 160, 81, 35);
        pruneFileNameAddFrontTextField.setForeground(Color.WHITE);
        pruneFileNameAddFrontTextField.setCaretColor(Color.WHITE);
        pruneFileNameAddFrontTextField.setFont(WtsConfig.buttonFont);
        pruneFileNameAddFrontTextField.setBackground(WtsConfig.bgColor);
        JRoundedTextField pruneFileNameAddTailTextField = new JRoundedTextField("suffix");
        pruneFileNameAddTailTextField.setBounds(310, 160, 82, 35);
        pruneFileNameAddTailTextField.setForeground(Color.WHITE);
        pruneFileNameAddTailTextField.setCaretColor(Color.WHITE);
        pruneFileNameAddTailTextField.setFont(WtsConfig.buttonFont);
        pruneFileNameAddTailTextField.setBackground(WtsConfig.bgColor);
        // Cut Front Button
        final JRoundedButton cutFrontBtn = new JRoundedButton("Cut Front");
        cutFrontBtn.setBounds(52, 200, 81, 35);
        cutFrontBtn.setForeground(Color.WHITE);
        cutFrontBtn.setFont(WtsConfig.buttonFont);
        cutFrontBtn.setBackground(WtsConfig.buttonBgColor);
        cutFrontBtn.setBorderPainted(false);
        cutFrontBtn.setFocusPainted(false);
        cutFrontBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = pruneFileNameTextField.getText().trim();
                if (folderStr.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists() || !folder.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                String text = pruneFileNameCutFrontTextField.getText();
                if (text.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Cut Front String Can't Be Empty !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameCutFrontTextField.grabFocus();
                    return;
                }
                Integer cutLength = null;
                try {
                    cutLength = Integer.parseInt(text);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Cut Front String Should Be A Number !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameCutFrontTextField.grabFocus();
                    return;
                }
                RenameFileService.cutFrontFileName(folder, cutLength);
                JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                cutFrontBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                cutFrontBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });
        // Cut Tail Button
        final JRoundedButton cutTailBtn = new JRoundedButton("Cut Tail");
        cutTailBtn.setBounds(138, 200, 81, 35);
        cutTailBtn.setForeground(Color.WHITE);
        cutTailBtn.setFont(WtsConfig.buttonFont);
        cutTailBtn.setBackground(WtsConfig.buttonBgColor);
        cutTailBtn.setBorderPainted(false);
        cutTailBtn.setFocusPainted(false);
        cutTailBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = pruneFileNameTextField.getText().trim();
                if (folderStr.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists() || !folder.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                String text = pruneFileNameCutTailTextField.getText();
                if (text.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Cut Tail String Can't Be Empty !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameCutTailTextField.grabFocus();
                    return;
                }
                Integer cutLength = null;
                try {
                    cutLength = Integer.parseInt(text);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Cut Tail String Should Be A Number !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameCutFrontTextField.grabFocus();
                    return;
                }
                RenameFileService.cutTailFileName(folder, cutLength);
                JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                cutTailBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                cutTailBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });
        // Add Front Button
        final JRoundedButton addFrontBtn = new JRoundedButton("Add Front");
        addFrontBtn.setBounds(224, 200, 81, 35);
        addFrontBtn.setForeground(Color.WHITE);
        addFrontBtn.setFont(WtsConfig.buttonFont);
        addFrontBtn.setBackground(WtsConfig.buttonBgColor);
        addFrontBtn.setBorderPainted(false);
        addFrontBtn.setFocusPainted(false);
        addFrontBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = pruneFileNameTextField.getText().trim();
                if (folderStr.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists() || !folder.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                String text = pruneFileNameAddFrontTextField.getText();
                if (text.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Add Front String Can't Be Empty !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameAddFrontTextField.grabFocus();
                    return;
                }
                RenameFileService.addFrontFileName(folder, text);
                JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addFrontBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addFrontBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });
        // Add Tail Button
        final JRoundedButton addTailBtn = new JRoundedButton("Add Tail");
        addTailBtn.setBounds(310, 200, 81, 35);
        addTailBtn.setForeground(Color.WHITE);
        addTailBtn.setFont(WtsConfig.buttonFont);
        addTailBtn.setBackground(WtsConfig.buttonBgColor);
        addTailBtn.setBorderPainted(false);
        addTailBtn.setFocusPainted(false);
        addTailBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = pruneFileNameTextField.getText().trim();
                if (folderStr.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists() || !folder.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameTextField.grabFocus();
                    return;
                }
                String text = pruneFileNameAddTailTextField.getText();
                if (text.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Add Tail String Can't Be Empty !", "Warning", JOptionPane.WARNING_MESSAGE);
                    pruneFileNameAddTailTextField.grabFocus();
                    return;
                }
                RenameFileService.addTailFileName(folder, text);
                JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addTailBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addTailBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });


        /** Replace File Name */
        JRoundedTextField replaceFileNameTextField = new JRoundedTextField("folder path");
        replaceFileNameTextField.setBounds(52, 260, 341, 35);
        replaceFileNameTextField.setForeground(Color.WHITE);
        replaceFileNameTextField.setCaretColor(Color.WHITE);
        replaceFileNameTextField.setFont(WtsConfig.buttonFont);
        replaceFileNameTextField.setBackground(WtsConfig.bgColor);
        JRoundedTextField replaceFileNameATextField = new JRoundedTextField("original ");
        replaceFileNameATextField.setBounds(52, 300, 165, 35);
        replaceFileNameATextField.setForeground(Color.WHITE);
        replaceFileNameATextField.setCaretColor(Color.WHITE);
        replaceFileNameATextField.setFont(WtsConfig.buttonFont);
        replaceFileNameATextField.setBackground(WtsConfig.bgColor);
        JRoundedTextField replaceFileNameBTextField = new JRoundedTextField("replacement");
        replaceFileNameBTextField.setBounds(222, 300, 171, 35);
        replaceFileNameBTextField.setForeground(Color.WHITE);
        replaceFileNameBTextField.setCaretColor(Color.WHITE);
        replaceFileNameBTextField.setFont(WtsConfig.buttonFont);
        replaceFileNameBTextField.setBackground(WtsConfig.bgColor);
        // Replace File Name Button
        final JRoundedButton replaceFileNameBtn = new JRoundedButton("Replace File Name");
        replaceFileNameBtn.setBounds(50, 340, 345, 35);
        replaceFileNameBtn.setForeground(Color.WHITE);
        replaceFileNameBtn.setFont(WtsConfig.buttonFont);
        replaceFileNameBtn.setBackground(WtsConfig.buttonBgColor);
        replaceFileNameBtn.setBorderPainted(false);
        replaceFileNameBtn.setFocusPainted(false);
        replaceFileNameBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String folderStr = replaceFileNameTextField.getText().trim();
                if (folderStr.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    replaceFileNameTextField.grabFocus();
                    return;
                }
                File folder = new File(folderStr);
                if (!folder.exists() || !folder.isDirectory()) {
                    JOptionPane.showMessageDialog(frame, "Bad Folder Path !", "Warning", JOptionPane.WARNING_MESSAGE);
                    replaceFileNameTextField.grabFocus();
                    return;
                }
                String aStr = replaceFileNameATextField.getText();
                String bStr = replaceFileNameBTextField.getText();
                if (aStr.length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Source String Can't Be Empty !", "Warning", JOptionPane.WARNING_MESSAGE);
                    replaceFileNameTextField.grabFocus();
                    return;
                }
                RenameFileService.replaceFileName(folder, aStr, bStr);
                JOptionPane.showMessageDialog(frame, "Success !", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                replaceFileNameBtn.setBackground(WtsConfig.buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                replaceFileNameBtn.setBackground(WtsConfig.buttonBgColor);
            }
        });


        /** Assemble Mode Panel */
        panel.add(renameFilesFolderTextField);
        panel.add(renameFilesBtn);
        panel.add(replaceFileNameTextField);
        panel.add(replaceFileNameATextField);
        panel.add(replaceFileNameBTextField);
        panel.add(replaceFileNameBtn);
        panel.add(pruneFileNameTextField);
        panel.add(pruneFileNameCutFrontTextField);
        panel.add(pruneFileNameCutTailTextField);
        panel.add(pruneFileNameAddFrontTextField);
        panel.add(pruneFileNameAddTailTextField);
        panel.add(cutFrontBtn);
        panel.add(cutTailBtn);
        panel.add(addFrontBtn);
        panel.add(addTailBtn);

        return panel;
    }

}