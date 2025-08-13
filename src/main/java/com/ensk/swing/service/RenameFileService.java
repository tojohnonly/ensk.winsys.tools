package com.ensk.swing.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class RenameFileService {

    public static void renameFolderFiles(File folder) {
        File[] files = folder.listFiles();
        int index = 0;
        for (File file : files) {
            index++;
            String modifiedDate = getFileDateName(file);
            if (null == modifiedDate) {
                continue;
            }
            String parentPath = file.getParent();
            String fileSuffix = null;
            if (file.isDirectory()) {
                fileSuffix = "." + file.getName();
            } else {
                if (file.getName().contains(".")) {
                    fileSuffix = "." + file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
                } else {
                    fileSuffix = "";
                }
            }
            File newFile = new File(parentPath + File.separator + modifiedDate + fileSuffix);
            // If the new file exists and is not the origin file, then add a sequence number after the new name
            if (newFile.exists() && !(newFile.getName().equals(file.getName()))) {
                newFile = new File(parentPath + File.separator + modifiedDate + index + fileSuffix);
            }
            boolean success = file.renameTo(newFile);
            System.out.println("[" + success + "] " + file + " >>>>> " + newFile);
        }
    }

    private static String getFileDateName(File file) {
        Date date = null;
        if (!file.isDirectory()) {
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(file);
                labekOuter:
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                        String directoryName = directory.getName();
                        String tagName = tag.getTagName();
                        // Image Info
                        if (Objects.equals(directoryName, "Exif SubIFD") && Objects.equals(tagName, "Date/Time Original")) {
                            // 2024:04:08 22:47:40
                            SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                            date = format.parse(tag.getDescription());
                            break labekOuter;
                        }
                        // Video Info
                        if (Objects.equals(directoryName, "QuickTime") && Objects.equals(tagName, "Creation Time")) {
                            // Mon May 06 20:36:33 +08:00 2024
                            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss XXX yyyy");
                            date = format.parse(tag.getDescription());
                            break labekOuter;
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            String regex = "^\\d{8}-\\d{6}\\..+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                System.out.println("Already Formated Folder Name");
                return null;
            }
        }
        if (null == date || date.getTime() <= 0) {
            date = new Date(file.lastModified());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String modifiedDate = dateFormat.format(date);
        return modifiedDate;
    }


    private static void rename(File file, String newFileName) {
        File newFile = new File(file.getParent(), newFileName);
        if (newFile.exists()) {
            System.out.println("File Already Exists, Skip: " + newFileName);
            return;
        }
        boolean success = file.renameTo(newFile);
        if (success) {
            System.out.println("Rename Success: " + file.getName() + " → " + newFileName);
        } else {
            System.out.println("Rename Failed: " + file.getName());
        }
    }

    public static void replaceFileName(File folder, String aStr, String bStr) {
        File[] files = folder.listFiles();
        for (File file : files) {
            String oldName = file.getName();
            if (file.isFile()) {
                int dotIndex = oldName.lastIndexOf('.');
                if (dotIndex > 0) {
                    String baseName = oldName.substring(0, dotIndex);
                    String extension = oldName.substring(dotIndex);
                    if (baseName.contains(aStr)) {
                        String newBaseName = baseName.replace(aStr, bStr);
                        rename(file, newBaseName + extension);
                    }
                } else {
                    if (oldName.contains(aStr)) {
                        String newName = oldName.replace(aStr, bStr);
                        rename(file, newName);
                    }
                }
            } else {
                if (oldName.contains(aStr)) {
                    String newName = oldName.replace(aStr, bStr);
                    rename(file, newName);
                }
            }
        }
    }

    public static void cutFrontFileName(File folder, Integer cutLength) {
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (file.isFile()) {
                int dotIndex = name.lastIndexOf('.');
                if (dotIndex <= 0) {
                    if (name.length() <= cutLength) {
                        System.out.println("Cut Front File Name Too Short, Skip: " + name);
                        continue;
                    }
                    rename(file, name.substring(cutLength));
                } else {
                    String baseName = name.substring(0, dotIndex);
                    String extension = name.substring(dotIndex);
                    if (baseName.length() <= cutLength) {
                        System.out.println("Cut Front File Name Too Short, Skip: " + name);
                        continue;
                    }
                    rename(file, baseName.substring(cutLength) + extension);
                }
            } else {
                if (name.length() <= cutLength) {
                    System.out.println("Cut Front Folder Name Too Short, Skip:" + name);
                    continue;
                }
                rename(file, name.substring(cutLength));
            }
        }
    }

    public static void cutTailFileName(File folder, Integer cutLength) {
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (file.isFile()) {
                int dotIndex = name.lastIndexOf('.');
                if (dotIndex <= 0) {
                    if (name.length() <= cutLength) {
                        System.out.println("Cut Tail File Name Too Short, Skip: " + name);
                        continue;
                    }
                    rename(file, name.substring(0, name.length() - cutLength));
                } else {
                    String baseName = name.substring(0, dotIndex);
                    String extension = name.substring(dotIndex);
                    if (baseName.length() <= cutLength) {
                        System.out.println("Cut Tail File Name Too Short, Skip: " + name);
                        continue;
                    }
                    rename(file, baseName.substring(0, baseName.length() - cutLength) + extension);
                }
            } else {
                if (name.length() <= cutLength) {
                    System.out.println("Cut Tail Folder Name Too Short, Skip: " + name);
                    continue;
                }
                rename(file, name.substring(0, name.length() - cutLength));
            }
        }
    }

    public static void addFrontFileName(File folder, String text) {
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            String newName;
            if (file.isFile()) {
                int dotIndex = name.lastIndexOf('.');
                if (dotIndex > 0) {
                    String baseName = name.substring(0, dotIndex);
                    String extension = name.substring(dotIndex);
                    newName = text + baseName + extension;
                } else {
                    newName = text + name;
                }
            } else {
                newName = text + name;
            }
            rename(file, newName);
        }
    }

    public static void addTailFileName(File folder, String text) {
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            String newName;
            if (file.isFile()) {
                int dotIndex = name.lastIndexOf('.');
                if (dotIndex > 0) {
                    String baseName = name.substring(0, dotIndex);
                    String extension = name.substring(dotIndex);
                    newName = baseName + text + extension;
                } else {
                    newName = name + text;
                }
            } else {
                newName = name + text;
            }
            rename(file, newName);
        }
    }
}
