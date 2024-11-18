package tests;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authors2Test extends BaseTest {
    private static WebDriverWait wait;
    @Test
    @Tag("firefox")
    public void extractAuthors() {
        List<String> allAuthorNames = new ArrayList<>();

        // İlk sayfayı işleme
        processPage("https://qaavenue.substack.com/p/issue-1-software-testing-notes", allAuthorNames);

        // Diğer sayfaları işleme
        for (int issueNo = 2; issueNo <= 20; issueNo++) {
            System.out.println("Issue: " + issueNo);
            processPage("https://qaavenue.substack.com/p/issue-" + issueNo + "-software-testing-insights", allAuthorNames);
        }

        // Yazarların tekrar sayısını hesapla
        Map<String, Integer> metrics = new HashMap<>();
        for (String author : allAuthorNames) {
            metrics.put(author, metrics.getOrDefault(author, 0) + 1);
        }

        // Popülerliğe göre sırala
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(metrics.entrySet());
        sorted.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

//        // Tüm yazarları ve sayılarını yazdır
//        System.out.println("All authors:");
//        for (Map.Entry<String, Integer> entry : sorted) {
//            System.out.println(entry.getKey() + ": " + entry.getValue() + " times");
//        }

        // Toplam yazar sayısını yazdır
        System.out.println("\nTotal number of unique authors: " + metrics.size());
    }

    private void processPage(String url, List<String> allAuthorNames) {
        WebDriver driver = getDriver();
        driver.get(url);

        try {
            WebElement closeIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='_closeIcon_1h9fv_121']")));
            Thread.sleep(3000); // Simülasyon gecikmesi
            closeIcon.click();
        } catch (Exception e) {
            System.out.println("Close icon not found. Skipping...");
        }

        List<WebElement> links = driver.findElements(By.xpath("//div[@class='available-content']//p//a"));

        List<String> excludeWords = List.of("QA Avenue", "Click here to submit your ideas!");

        List<String> authorNames = new ArrayList<>();
        for (WebElement link : links) {
            String linkText = link.getText();
            boolean shouldExclude = false;
            for (String excludeWord : excludeWords) {
                if (linkText.contains(excludeWord)) {
                    shouldExclude = true;
                    break;
                }
            }
            if (!shouldExclude) {
                authorNames.add(linkText);
            }
        }

        try {
            Thread.sleep(1000); // Server'a aşırı yük bindirmemek için
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        allAuthorNames.addAll(authorNames);
    }
}
