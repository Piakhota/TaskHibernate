package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Util {
    // реализуйте настройку соеденения с БД
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private Util() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
