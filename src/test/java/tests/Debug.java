package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;


public class Debug extends BaseTest {
    @Test
    public void debug() {
        driver.get("https://www.youtube.com/");
        driver.navigate().back();
    }
}
