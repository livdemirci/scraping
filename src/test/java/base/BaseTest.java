package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTest {

    // Her thread için bağımsız WebDriver'lar sağlamak için ThreadLocal kullanıyoruz
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    // ConfigReader sınıfını kullanarak tarayıcı ve debug modunu alacağız
    private String browser = ConfigReader.getBrowser();  // Tarayıcı tipi (chrome/firefox vb.)
    private boolean useDebugMode = ConfigReader.useRemoteDebugging();  // Debug modunu al

    @BeforeEach
    public void setUp() throws Exception {
        if (threadLocalDriver.get() == null) {
            initializeDriver();
        }
    }

    private void initializeDriver() {
        // Tarayıcı tipine göre WebDriver'ı başlatıyoruz
        switch (browser.toLowerCase()) {
            case "chrome":
                threadLocalDriver.set(initializeChromeDriver(useDebugMode));
                break;
            case "firefox":
                threadLocalDriver.set(initializeFirefoxDriver());
                break;
            default:
                throw new IllegalArgumentException("Desteklenmeyen tarayıcı: " + browser);
        }
    }

    private WebDriver initializeChromeDriver(boolean useDebugMode) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");

        // Eğer debug mode aktifse, gerekli tüm debug argümanlarını ekliyoruz
        if (useDebugMode) {
            options.addArguments(
                    "--remote-debugging-port=9222",       // Remote debugging portu
                    "--user-data-dir=/tmp/ChromeProfile",  // Kullanıcı profili
                    "--disk-cache-dir=null",               // Disk cache kapalı
                    "--overscroll-history-navigation=0",  // Geçmişi engelle
                    "--disable-web-security"              // Web güvenliği kapalı
            );
        }

        return new ChromeDriver(options);
    }

    private WebDriver initializeFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();

        // Firefox için gerekli ayarları yapıyoruz
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");

        // Debug modunda ise gerekli ayarları yapıyoruz
        if (useDebugMode) {
            options.addArguments(
                    "-start-debugger-server",  // Firefox remote debugging için başlatma
                    "--remote-debugging-port=9222"  // Debugging portu
            );
        }

        return new FirefoxDriver(options);  // FirefoxDriver'ı başlatıyoruz
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
