package helper;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;


public class SeleniumHelper {

    private static final int TIMEOUT_MS = 2000;
    private static final int TIMEOUT_SECONDS = 40;
    private static final int POLL_EVERY_MS = 50;
    public static int DRIVER_WAIT = 10;
    private RemoteWebDriver driver;
    private ExtentTest EXTENT_TEST_LOGGER;
    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumHelper.class);

    public SeleniumHelper(final RemoteWebDriver driver, final ExtentTest EXTENT_TEST_LOGGER) {
        this.driver = driver;
        this.EXTENT_TEST_LOGGER = EXTENT_TEST_LOGGER;
    }

    public void sendKeysUsingAction(String keys) {
        Actions action = new Actions(driver);
        action.sendKeys(keys).build().perform();
    }


    public WebDriverWait newWait() {
        return new WebDriverWait(driver, TIMEOUT_SECONDS);
    }


    public String getSelectedOptionFromDropdown(final WebElement webElement) {
        final String text = new Select(webElement).getFirstSelectedOption().getText();
        return text;
    }

    public void selectedOptionFromDropdown(final WebElement webElement, final String value) {
        webElement.click();
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(value);
    }

    public WebElement findChildElementSearchByText(String MainMenu, String value) {
        return driver.findElements(By.xpath(MainMenu)).stream().filter(webElement -> webElement.getText().contains(value))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Could not find menu item with id'%s'", value)));

    }

    public String ElementGetText(WebElement Element) {
        String ElementText = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            boolean isElementDisplayed = Element.isDisplayed();
            if (isElementDisplayed) {
                ElementText = Element.getText();
                if (!ElementText.isEmpty()) {
                    return ElementText;
                } else {
                    EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element provided do not have text value please check");
                }
            }
        } catch (Exception e) {
            return ElementText;
        }
        return ElementText;
    }


    public void scrollElementToVerticalCenter(final WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center'});",
                element);
        Thread.sleep(TIMEOUT_MS);
    }

    public boolean isDisplayed(final WebElement element) {
        return isDisplayed(element, 5);
    }

    public boolean isDisplayed(final WebElement element, final long timeOutInSeconds) {
        try {
            new WebDriverWait(driver, timeOutInSeconds).pollingEvery(200, TimeUnit.MILLISECONDS)
                    .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                    .until(ExpectedConditions.visibilityOf(element));
            if (!element.getText().isEmpty()) {
                EXTENT_TEST_LOGGER.log(LogStatus.PASS, "Element is Visible: " + element.getText());
            } else {
                EXTENT_TEST_LOGGER.log(LogStatus.PASS, "Element is Visible: " + element);
            }
            return true;
        } catch (TimeoutException e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element is not Visible: " + element.toString());
            return false;
        }
    }


    public void waitUntilElementNotVisible(final By by) {
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public <T> T waitUntil(Function<WebDriver, ?> function) {
        return waitUntil(POLL_EVERY_MS, function);
    }

    public <T> T waitUntil(int pollEveryMs, Function<WebDriver, ?> function) {
        return waitUntil(pollEveryMs, TIMEOUT_SECONDS, function);
    }

    public <T> T waitUntil(int pollEveryMs, long timeOutAfterSeconds, Function<WebDriver, ?> function) {
        final WebDriverWait wait = new WebDriverWait(driver, timeOutAfterSeconds);
        wait.pollingEvery(pollEveryMs, TimeUnit.MILLISECONDS);
        return (T) wait.until(function);
    }


    public String hoverOnElementAndGetText(WebElement MainMenu, WebElement SubMenu,
                                           String... description) {
        String myString = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            WebElement mainMenu = MainMenu;
            Actions actions = new Actions(driver);
            actions.moveToElement(mainMenu).build().perform();
            myString = SubMenu.getText();
            int WhileCntr = 1;
            while (myString.isEmpty()) {
                actions.moveToElement(mainMenu).build().perform();
                Thread.sleep(TIMEOUT_MS);
                myString = SubMenu.getText();
                Thread.sleep(TIMEOUT_MS);
                if (WhileCntr > 3) {
                    LOGGER.error("Element not found" + SubMenu);
                }
                WhileCntr = WhileCntr + 1;

            }
        } catch (Exception e) {
            LOGGER.error("Element not found" + SubMenu);
        }
        return myString;
    }

}
