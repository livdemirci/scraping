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

    // Tarayıcı tipini alıyoruz (Pipeline'dan gelen ortam değişkenini kontrol et)
    public static String getBrowser() {
        String browser = System.getenv("BROWSER");  // Pipeline'dan gelen BROWSER ortam değişkeni
        if (browser == null || browser.isEmpty()) {
            browser = properties.getProperty("browser");  // Fallback: configuration.properties dosyasından al
        }
        return browser;  // chrome varsayılan
    }

    // Remote Debugging modunu kontrol ediyoruz
    public static boolean useRemoteDebugging() {
        return Boolean.parseBoolean(properties.getProperty("remoteDebug", "true"));  // false varsayılan
    }
}
