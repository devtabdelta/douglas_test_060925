package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private By shadowRoot = By.id("usercentrics-root");
    private By acceptAllButton = By.cssSelector(".sc-dcJsrY.eIFzaz");
    private Logger logger = LoggerFactory.getLogger(HomePage.class);
    private WebDriverWait wait;

    @FindBy(xpath = "//a[contains(@href, '/de/c/parfum/')]")
    private WebElement perfumeLink;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Open Douglas homepage")
    public void openHomePage() {
        driver.get("https://www.douglas.de/de");
    }

    @Step("Accept cookies if displayed (shadow DOM)")
    public void acceptCookies() {
        try {
            // Wait for the shadow host to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(shadowRoot));
            WebElement shadowHostElement = driver.findElement(shadowRoot);
            SearchContext shadowRootContext = shadowHostElement.getShadowRoot();

            // Wait for the accept button inside shadow DOM
            WebElement acceptButton = wait.until(d -> shadowRootContext.findElement(acceptAllButton));
            acceptButton.click();
            logger.info("Cookies accepted successfully.");
        } catch (NoSuchElementException e) {
            logger.error("Failed to locate shadow root or accept button: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An error occurred while accepting cookies: {}", e.getMessage(), e);
        }
    }

    @Step("Click Perfume section")
    public void clickPerfumeLink() {
        click(perfumeLink);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
