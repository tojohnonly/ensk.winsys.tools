package com.ensk.swing.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
        Date date = new Date(file.lastModified());
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
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String modifiedDate = dateFormat.format(date);
        return modifiedDate;
    }

}
