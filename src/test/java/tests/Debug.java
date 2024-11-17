package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;


public class Debug extends BaseTest {
    @Test
    public void debug() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://www.youtube.com/");
        driver.navigate().back();
        Thread.sleep(3000);
    }
}
