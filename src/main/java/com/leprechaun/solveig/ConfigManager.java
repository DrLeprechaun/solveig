/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig;

/**
 *
 * @author v_emelyanov
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigManager {
    
    public ConfigManager() {}

    public void writeProperty(String fileName, String propertyName, String propertyValue) {
        
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            //output = new FileOutputStream(fileName);
            output = new FileOutputStream(execPath() + "\\classes\\" + fileName);

            // set the properties value
            prop.setProperty(propertyName, propertyValue);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    
    public void writeConnectionProperties(String host, String port, String username, String password) {
        
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(execPath() + "\\classes\\connection.properties");

            // set the properties values
            prop.setProperty("host", host);
            prop.setProperty("port", port);
            prop.setProperty("username", username);
            prop.setProperty("password", password);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String readProperty(String fileName, String propertyName) {

        String propertyValue = new String();

        Properties prop = new Properties();
        InputStream input = null;

        try {

            //input = new FileInputStream(fileName);
            input = new FileInputStream(execPath() + "\\classes\\" + fileName);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            propertyValue = prop.getProperty(propertyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return propertyValue;
    }

    private String execPath() {
        String absolute = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        absolute = absolute.substring(0, absolute.length() - 1);
        absolute = absolute.substring(0, absolute.lastIndexOf("/") + 1);
        String configPath = absolute;
        String os = System.getProperty("os.name");
        if (os.indexOf("Windows") != -1) {
            configPath = configPath.replace("/", "\\\\");
            if (configPath.indexOf("file:\\\\") != -1) {
                configPath = configPath.replace("file:\\\\", "");
            }
        } else if (configPath.indexOf("file:") != -1) {
            configPath = configPath.replace("file:", "");
        }
        
        return configPath;
    }
    
}
