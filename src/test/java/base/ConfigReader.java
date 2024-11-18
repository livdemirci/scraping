package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    // properties dosyasını yüklüyoruz
    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/configuration.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tarayıcı tipini alıyoruz
    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");  // chrome varsayılan
    }

    // Remote Debugging modunu kontrol ediyoruz
    public static boolean useRemoteDebugging() {
        return Boolean.parseBoolean(properties.getProperty("remoteDebug", "true"));  // false varsayılan
    }
}
