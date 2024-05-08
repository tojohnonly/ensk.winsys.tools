package com.ensk.swing.service;

import java.io.IOException;

public class RemoveOneDriveIconService {

    public static void removeOneDriveIcon() {
        try {
            Runtime.getRuntime().exec("cmd /c reg add HKEY_CLASSES_ROOT\\CLSID\\{018D5C66-4533-4307-9B53-224DE2ED1FE6} /v System.IsPinnedToNameSpaceTree /t REG_DWORD /d 0 /f");
            Thread.sleep(1000);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
