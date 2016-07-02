package com.cevaris.nike.server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig {

    private Logger log = Logger.getLogger(ServerConfig.class);

    public final static String SERVER_THRIFT_PORT = "server.thrift.port";

    private final Properties prop = new Properties();

    public ServerConfig(String propsPath) {
        PropertyConfigurator.configure(propsPath);

        File file = new File(propsPath);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            log.info(String.format("loading server config %s", file.getAbsolutePath()));

            prop.load(reader);
        } catch (FileNotFoundException e) {
            log.error("config lookup", e);
        } catch (IOException e) {
            log.error("config lookup", e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getString(String key) {
        return prop.getProperty(key);
    }

    public Integer getInt(String key) {
        return Integer.parseInt(prop.getProperty(key));
    }

    public Long getLong(String key) {
        return Long.parseLong(prop.getProperty(key));
    }

    public Properties getProperties() {
        return prop;
    }
}
