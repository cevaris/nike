package com.cevaris.nike.server;

import com.sun.javafx.fxml.PropertyNotFoundException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BrokerConfig {

    private Logger log = Logger.getLogger(BrokerConfig.class);

    private final Properties prop = new Properties();

    public final static String SERVER_THRIFT_PORT = "server.thrift.port";
    public final static String DATA_DIR = "server.data.dir";

    public BrokerConfig(String propsPath) {
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

    public Integer getInt(String key) {
        return Integer.parseInt(getOrThrow(key));
    }

    public Long getLong(String key) {
        return Long.parseLong(getOrThrow(key));
    }

    private String getOrThrow(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            throw new PropertyNotFoundException(key);
        } else {
            return value;
        }
    }
}
