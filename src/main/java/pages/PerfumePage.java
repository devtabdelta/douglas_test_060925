package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class PerfumePage extends BasePage {
    private WebDriverWait wait;

    public PerfumePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void applyFilter(String filterName, String option) {
        try {
            WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), '" + filterName + "')]")
            ));
            filter.click();
            Thread.sleep(1000);
            WebElement filterOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(), '" + option + "')]")
            ));
            filterOption.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            // Filter or option not found
        }
    }

    public List<WebElement> getProducts() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'product') or contains(@class, 'item')]")
        ));
        return driver.findElements(
            By.xpath("//div[contains(@class, 'product') or contains(@class, 'item')]")
        );
    }
}