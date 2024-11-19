package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Debug extends BaseTest {
    @Test
    public void debug() throws InterruptedException {
        WebDriver driver = getDriver();
//        WebElement element = driver.findElement(By.xpath("//*[@class=\"flex-1 truncate shrink-0\"]/..//*[@class=\"object-contain w-12 h-12 overflow-hidden pointer-events-none\"]"));
        WebElement element2 = driver.findElement(By.xpath("//div[text()='Abonelik Ekle']/.."));
//        textarea.sendKeys("alican");
    element2.click();

        Thread.sleep(3000);
    }
}
