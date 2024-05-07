package com.ensk.swing.service;

import java.io.File;
import java.io.IOException;

public class ClearIconCacheService {

    public static void clearIconCache() {
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

}
