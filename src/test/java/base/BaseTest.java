package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    // Her thread için bağımsız WebDriver'lar sağlamak için ThreadLocal kullanıyoruz
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    boolean useDebugMode = false; // Debug modunu aktif hale getirin

    @BeforeEach
    public void setUp() throws Exception {
        if (threadLocalDriver.get() == null) {
            if (useDebugMode) {
                connectToChromeDebugMode();
            } else {
                // WebDriver her thread için ayrı oluşturulacak
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
                threadLocalDriver.set(new ChromeDriver(options));  // WebDriver'ı ThreadLocal'a atıyoruz
            }
        }
    }

    public void connectToChromeDebugMode() throws InterruptedException {
        try {
            // Eğer driver zaten mevcutsa, sadece mevcut oturuma bağlan
            if (threadLocalDriver.get() != null) {
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
            threadLocalDriver.set(new ChromeDriver(options));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove(); // Bellek sızıntısını önlemek için
        }
    }

    // WebDriver'ı her test için almak için bir getter metodu
    public WebDriver getDriver() {
        return threadLocalDriver.get();
    }
}
