package com.ensk.swing.config;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WtsConfig {

    public static Properties properties = getProperties();
    public static Color bgColor = getBgColor();
    public static Font buttonFont = new Font("Microsoft YaHei UI", Font.PLAIN, 15);
    public static Color buttonBgColor = getButtonBgColor();
    public static Color buttonHoverColor = getButtonHoverColor();

    private static Properties getProperties() {
        String currentDir = System.getProperty("user.dir");
        String configFilePath = currentDir + "/wts.conf";
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configFilePath);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static Color getBgColor() {
        Integer red = Integer.valueOf(properties.getProperty("bg.color.red"));
        Integer green = Integer.valueOf(properties.getProperty("bg.color.green"));
        Integer blue = Integer.valueOf(properties.getProperty("bg.color.blue"));
        return new Color(red, green, blue);
    }

    private static Color getButtonBgColor() {
        Integer red = Integer.valueOf(properties.getProperty("button.bg.color.red"));
        Integer green = Integer.valueOf(properties.getProperty("button.bg.color.green"));
        Integer blue = Integer.valueOf(properties.getProperty("button.bg.color.blue"));
        return new Color(red, green, blue);
    }

    private static Color getButtonHoverColor() {
        Integer red = Integer.valueOf(properties.getProperty("button.bg.color.red"));
        Integer green = Integer.valueOf(properties.getProperty("button.bg.color.green"));
        Integer blue = Integer.valueOf(properties.getProperty("button.bg.color.blue"));
        return new Color(red, green, blue);
    }

}
