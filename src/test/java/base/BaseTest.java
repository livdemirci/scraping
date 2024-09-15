package base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class BaseTest {
    protected WebDriver driver;
    boolean useDebugMode = false; // Debug modunu aktif hale getirin

    @Before
    public void setUp() throws Exception {
        if (driver == null) {
            if (useDebugMode) {
                connectToChromeDebugMode();
            } else {
                driver = new ChromeDriver();
            }
        }
    }

    public void connectToChromeDebugMode() throws InterruptedException {
        try {
            // Eğer driver zaten mevcutsa, sadece mevcut oturuma bağlan
            if (driver != null) {
                return;
            }

            // Mevcut Chrome oturumunu başlatmak için
            String command = "cmd.exe /c start \"\" \"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" " +
                    "--remote-debugging-port=9222 " +
                    "--user-data-dir=C:\\Temp\\ChromeProfile " +
                    "--disk-cache-dir=null " +
                    "--overscroll-history-navigation=0 " +
                    "--disable-web-security";

            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            Thread.sleep(3000); // Chrome'un başlatılması için zaman tanıyın

            System.out.println("Process completed");

            Thread.sleep(5000); // Mevcut oturuma bağlanmak için zaman tanıyın

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

            // Mevcut Chrome oturumuna bağlan
            driver = new ChromeDriver(options);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // Bellek sızıntısını önlemek için
        }
    }
}
