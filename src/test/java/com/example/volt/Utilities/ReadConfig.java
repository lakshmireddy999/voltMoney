package com.example.volt.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.example.volt.Utilities.enums.Environment;

public class ReadConfig {
    Properties pro;

    public ReadConfig() {
        try {
            File src = new File("./Configuration/config.properties");
            FileInputStream fis = new FileInputStream(src);
            pro = new Properties();
            pro.load(fis);
        } catch (Exception e) {
            System.out.println("Exception is" + e.getMessage());
        }
    }

    public String getBrowser() {
        return pro.getProperty("browser");
    }

    public boolean getMode() {
        return Boolean.parseBoolean(pro.getProperty("headlessMode"));
    }

    public Environment getEnvironment() {
        return Environment.valueOf(pro.getProperty("environment"));
    }

}
