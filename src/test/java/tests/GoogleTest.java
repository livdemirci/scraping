package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class GoogleTest extends BaseTest {
    @Test
    public void go() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://app.abonesepeti.com/subscriptions");

        Thread.sleep(3000);
    }
}