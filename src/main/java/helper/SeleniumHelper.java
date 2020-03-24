package helper;


import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;



public class SeleniumHelper {

    private static final int TIMEOUT_MS = 2000;
    private static final int TIMEOUT_SECONDS = 40;
    private static final int POLL_EVERY_MS = 50;
    public static int DRIVER_WAIT = 10;
    private RemoteWebDriver driver;
    private Properties config;
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

    public boolean hoverOnElement(String identifierType, String identifier,
                                  String description) throws InterruptedException {
        boolean result = false;
        By by = null;
        if (identifierType.contains("xpath")) {
            by = By.xpath(identifier);

        } else if (identifier.contains("id")) {
            by = By.id(identifier);
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            WebElement MainMenu = driver.findElement(by);
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(1000);
        } catch (NoSuchElementException e) {

        }
        return result;
    }


    public WebDriverWait newWait() {
        return new WebDriverWait(driver, TIMEOUT_SECONDS);
    }

    public WebDriverWait newWait(final long timeOutInSeconds) {
        return new WebDriverWait(driver, timeOutInSeconds);
    }

    public WebElement waitForSubElement(final WebElement element, final By subLocator) {
        return newWait().until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, subLocator));
    }

    public boolean hoverOnElement(By by,
                                  String... description) {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WebElement MainMenu = driver.findElement(by);
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(100);
            return true;
        } catch (Exception e) {
            return result;
        }

    }

    public boolean hoverOnElement(WebElement elm,
                                  String... description) {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(elm));
            WebElement MainMenu = elm;
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(100);
            return true;
        } catch (Exception e) {
            return result;
        }

    }


    @SuppressWarnings("unchecked")
    protected <T> T waitUntil(long timeOutInSeconds, Function<WebDriver, ?> function) {
        return this.waitUntil(POLL_EVERY_MS, timeOutInSeconds, function);
    }

    public WebElement waitUntilElementToBeClickable(final By by) {
        return waitUntil(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitUntilElementToBeClickable(final WebElement we) {
        return waitUntil(ExpectedConditions.elementToBeClickable(we));
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


    public boolean hoverOnElementAndClick(By MainMenu, By SubMenu,
                                          String... description) throws Exception {
        Thread.sleep(TIMEOUT_MS);
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(MainMenu));
            wait.until(ExpectedConditions.visibilityOfElementLocated(MainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            WebElement mainMenu = driver.findElement(MainMenu);
            //Adding for firefox
            if (driver.getCapabilities().getBrowserName().equalsIgnoreCase("firefox")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].onmouseover()", mainMenu);
            } else {
                Actions actions = new Actions(driver);
                actions.moveToElement(mainMenu).build().perform();
            }
            Thread.sleep(TIMEOUT_MS);
            driver.findElement(SubMenu).click();
            return true;
        } catch (Exception e) {
            throw new Exception("Element not found" + SubMenu);
        }
    }


    public boolean hoverOnElementAndClick(String mainMenu, String subMenu,
                                          String... description) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            By MainMenu = By.xpath(mainMenu);
            By SubMenu = By.xpath(subMenu);
            wait.until(ExpectedConditions.presenceOfElementLocated(MainMenu));
            wait.until(ExpectedConditions.visibilityOfElementLocated(MainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            WebElement mainElement = driver.findElement(MainMenu);
            Actions actions = new Actions(driver);
            actions.moveToElement(mainElement).build().perform();
            Thread.sleep(TIMEOUT_MS);
            driver.findElement(SubMenu).click();
            return true;
        } catch (Exception e) {
            throw new Exception("Element not found" + subMenu);
        }
    }


    public boolean hoverOnElementAndClick(String mainMenu, By subMenu,
                                          String... description) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            By MainMenu = By.xpath(mainMenu);
            wait.until(ExpectedConditions.presenceOfElementLocated(MainMenu));
            wait.until(ExpectedConditions.visibilityOfElementLocated(MainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            WebElement mainElement = driver.findElement(MainMenu);
            Actions actions = new Actions(driver);
            actions.moveToElement(mainElement).build().perform();
            Thread.sleep(TIMEOUT_MS);
            driver.findElement(subMenu).click();
            return true;
        } catch (Exception e) {
            throw new Exception("Element not found" + subMenu);
        }
    }


    public void click(final By by) {
        click(driver.findElement(by));
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

    public void input(final WebElement input, final String text) {
        new WebDriverWait(driver, 20).ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                .until((ExpectedCondition<Boolean>) webDriver -> {
                    click(input);
                    input.clear();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.DELETE);
                    input.sendKeys(Keys.TAB);
                    click(input);
                    input.sendKeys(text);
                    return input.getAttribute("value").equals(text);
                });
    }

    public void click(final WebElement element) {
        newWait().ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(WebDriverException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.and(ExpectedConditions.elementToBeClickable(element),
                        ExpectedConditions.visibilityOf(element)));
        element.click();
    }

    public WebElement findChildElementSearchByText(String MainMenu, String value) {
        return driver.findElements(By.xpath(MainMenu)).stream().filter(webElement -> webElement.getText().contains(value))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Could not find menu item with id'%s'", value)));

    }

    public boolean hoverOnElementAndClick(WebElement MainMenu, By SubMenu,
                                          String... description) throws Exception {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(TIMEOUT_MS);
            driver.findElement(SubMenu).click();
            return true;
        } catch (Exception e) {
            throw new Exception("Element not found" + SubMenu);
        }
    }


    public boolean hoverOnElementAndClick(WebElement MainMenu, String SubMenu,
                                          String... description) throws Exception {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(TIMEOUT_MS);
            driver.findElement(By.xpath(SubMenu)).click();

            return true;
        } catch (Exception e) {
            throw new Exception("Element not found" + SubMenu);
        }
    }


    public boolean hoverOnElementAndClick(WebElement MainMenu, WebElement SubMenu,
                                          String... description) throws Exception {

        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(MainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(MainMenu));
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(TIMEOUT_MS);
            WebDriverWait waitForElement = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(SubMenu));
            ElementClick(SubMenu);
            return true;
        } catch (Exception e) {
            throw new Exception("Element not found" + SubMenu);
        }
    }


    public boolean hoverOnElementAndVerifyElement(WebElement MainMenu, By SubMenu,
                                                  String... description) throws Exception {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(TIMEOUT_MS);
            return driver.findElement(SubMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean hoverOnElementAndVerifyElement(WebElement MainMenu, WebElement SubMenu,
                                                  String... description) throws Exception {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(TIMEOUT_MS);
            return SubMenu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean hoverOnElement(WebElement element,
                                  String description) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(element).build().perform();
            Thread.sleep(TIMEOUT_MS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean moveToElementUsingActions(By mainMenu, By subMenu) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(mainMenu));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(mainMenu));
            final WebElement MainMenu = driver.findElement(mainMenu);
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(100);
            driver.findElement(subMenu).click();
            Thread.sleep(TIMEOUT_MS);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    public boolean moveToElementUsingActions(WebElement mainMenu, WebElement subMenu) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(mainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(mainMenu));
            final WebElement MainMenu = mainMenu;
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            Thread.sleep(100);
            subMenu.click();
            Thread.sleep(TIMEOUT_MS);
            return true;
        } catch (Exception e) {
            return false;
        }


    }


    public boolean moveToElementUsingJavaScript(By mainMenu, By subMenu) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(mainMenu));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(mainMenu));
            final WebElement MainMenu = driver.findElement(mainMenu);
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click;", driver.findElement(subMenu));
            Thread.sleep(TIMEOUT_MS);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean moveToElementUsingJavaScript(WebElement mainMenu, WebElement subMenu) {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(mainMenu));
            wait.until(ExpectedConditions.elementToBeClickable(mainMenu));
            final WebElement MainMenu = mainMenu;
            Actions actions = new Actions(driver);
            actions.moveToElement(MainMenu).build().perform();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click;", subMenu);
            Thread.sleep(TIMEOUT_MS);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean robotMoveToElement(WebElement mainMenu, By subMenu, int XCoordinate, int YCoordinat) {
        try {
            final Robot robot = new Robot();
            Thread.sleep(100);
            Actions actions = new Actions(driver);
            robot.mouseMove(mainMenu.getLocation().getX() + XCoordinate, mainMenu.getLocation().getY() + YCoordinat);
            actions.moveToElement(mainMenu).build().perform();
            Thread.sleep(100);
            driver.findElement(subMenu).click();
            Thread.sleep(100);
            return true;
        } catch (Exception e) {
            return false;

        }
    }

    public boolean robotMoveToElement(WebElement mainMenu, WebElement subMenu, int XCoordinate, int YCoordinat) {
        try {
            final Robot robot = new Robot();
            Thread.sleep(100);
            Actions actions = new Actions(driver);
            robot.mouseMove(mainMenu.getLocation().getX() + XCoordinate, mainMenu.getLocation().getY() + YCoordinat);
            actions.moveToElement(mainMenu).build().perform();
            Thread.sleep(100);
            subMenu.click();
            Thread.sleep(100);
            return true;
        } catch (Exception e) {
            return false;

        }

    }


    public boolean robotMoveToElement(int X, int Y) {
        try {
            final Robot robot = new Robot();
            Thread.sleep(100);
            Actions actions = new Actions(driver);
            robot.mouseMove(100, 100);
            Thread.sleep(100);
            robot.mouseMove(X, Y);
            return true;
        } catch (Exception e) {
            return false;

        }

    }

    public boolean IsElementDisplayed(By Element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            WebElement myElement = driver.findElement(Element);
            if (myElement.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean IsElementDisplayed(WebElement Element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            WebElement myElement = Element;
            if (myElement.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }


    public boolean IsElementDisplayed(String element) {
        By Element = By.xpath(element);
        try {
            Thread.sleep(TIMEOUT_MS);
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            WebElement myElement = driver.findElement(Element);
            if (myElement.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }


    public String ElementGetText(By Element) throws Exception {
        String ElementText = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            boolean isElementDisplayed = IsElementDisplayed(Element);
            if (isElementDisplayed) {
                WebElement myElement = driver.findElement(Element);
                ElementText = myElement.getText();
                if (!ElementText.isEmpty()) {
                    return ElementText;
                } else {
                    EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element provided do not have text value please check");
                }
            }
        } catch (Exception e) {

            return ElementText.trim();
        }
        return ElementText.trim();
    }


    public String ElementGetText(String element) throws Exception {
        By Element = By.xpath(element);
        String ElementText = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            boolean isElementDisplayed = IsElementDisplayed(Element);
            if (isElementDisplayed) {
                WebElement myElement = driver.findElement(Element);
                ElementText = myElement.getText();
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


    public boolean ElementClick(WebElement Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Element.click();
            return true;
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
            throw new Exception("Element not found" + Element);
        }

    }

    public void ElementClick(WebElement Element, String SendText) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Element.sendKeys(SendText);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }

    }


    public boolean ElementClick(By Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            driver.findElement(Element).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void ElementSendText(By Element, String SendText) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            driver.findElement(Element).sendKeys(SendText);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }
    }


    public void ElementSendText(WebElement Element, String SendText) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Element.sendKeys(SendText);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }
    }


    public void SendKeyEnter(By Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            driver.findElement(Element).sendKeys(Keys.ENTER);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }
    }


    public void SendKeyEnter(WebElement Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }
    }


    public void SendKeyTab(By Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            driver.findElement(Element).sendKeys(Keys.TAB);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }
    }


    public void SendKeyTab(WebElement Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Element.sendKeys(Keys.TAB);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
        }
    }

    public void scrollElementToVerticalCenter(final WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center'});",
                element);
        Thread.sleep(TIMEOUT_MS);
    }

    public void scrollElementToVerticalCenter(final By by) throws InterruptedException {
        final WebElement webElement = waitUntilElementVisible(by);
        scrollElementToVerticalCenter(webElement);
    }

    public WebElement waitUntilElementVisible(final By by) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public boolean ElementClick(String element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            By Element = By.xpath(element);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            driver.findElement(Element).click();
            return true;
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + element);
            throw new Exception("Element not found" + element);
        }
    }


    public void ElementSendText(String element, String SendText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            By Element = By.xpath(element);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            driver.findElement(Element).sendKeys(SendText);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + element);
        }
    }


    public boolean JavaScriptScrollToViewElement(WebElement Element) throws Exception {
        try {
            if (Element.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Element);
                Thread.sleep(500);
            }
            return true;
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
            throw new Exception("Element not found" + Element);
        }
    }


    public boolean JavaScriptScrollToViewElement(String StringElementPath) throws Exception {
        WebElement Element = driver.findElement(By.xpath(StringElementPath));
        try {
            if (Element.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Element);
                Thread.sleep(500);
            }
            return true;
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
            throw new Exception("Element not found" + Element);
        }
    }


    public String GetXpath(WebElement ByObject) {
        String str = ByObject.toString().split("xpath:")[1].trim();
        str = str.substring(0, str.length() - 1);
        return str;
    }


    public boolean JavaScriptScrollToViewElement(By Element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            //WebElement
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(Element));
            Thread.sleep(500);
            return true;
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element not found" + Element);
            throw new Exception("Element not found" + Element);
        }
    }


    public boolean WaitforElementToVanish(By ByElement) throws Exception {

        int WhileLoopCntr = 0;
        while (IsElementDisplayed(ByElement)) {
            Thread.sleep(SeleniumHelper.TIMEOUT_MS);
            if (WhileLoopCntr > 4) {
                return false;
            }
            WhileLoopCntr = WhileLoopCntr + 1;
        }
        return true;
    }


    public void waitforElementToVanish(By Element) throws Exception {
        int WhileLoopCntr = 0;

        while (IsElementDisplayed(Element)) {
            if (WhileLoopCntr > 4) {
                EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Request processing is slow and element is not disappearing" + Element);
            }
        }
    }


    public boolean WaitforElementToVanish(WebElement Element) throws InterruptedException {

        int WhileLoopCntr = 0;
        while (IsElementDisplayed(Element)) {
            Thread.sleep(TIMEOUT_MS);
            if (WhileLoopCntr > 4) {
                return false;
            }
            WhileLoopCntr = WhileLoopCntr + 1;
        }
        return true;
    }


    public void waitforElementToVanish(WebElement Element) throws Exception {
        int WhileLoopCntr = 0;
        while (IsElementDisplayed(Element)) {
            if (WhileLoopCntr > 4) {
                EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Request processing is slow and element is not disappearing" + Element);
            }
        }
    }


    public void SelectOptionsFromDropDown(WebElement Element, String SelectOptions) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            WebElement webElement = Element;
            Select Dropdown = new Select(webElement);
            Dropdown.selectByValue(SelectOptions);
            Thread.sleep(TIMEOUT_MS);
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element is not selected: " + SelectOptions);
        }
    }


    public String GetSelectedItemFromDropDown(By Element) throws Exception {
        String SelectedItem = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            WebElement webElement = driver.findElement(Element);
            Select Dropdown = new Select(webElement);
            SelectedItem = Dropdown.getFirstSelectedOption().getText();
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element is not selected: " + SelectedItem);
        }
        return SelectedItem;
    }


    public String GetSelectedItemFromDropDown(WebElement Element) throws Exception {
        String SelectedItem = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            WebElement webElement = Element;
            Select Dropdown = new Select(webElement);
            SelectedItem = Dropdown.getFirstSelectedOption().getText();
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "Element is not selected: " + SelectedItem);
        }
        return SelectedItem;
    }


    public boolean VerifyDropDownSelectedItem(By Element, String SelectOptions) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(Element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Select Dropdown = new Select(driver.findElement(Element));
            WebElement SelectedElement = Dropdown.getFirstSelectedOption();
            if (ElementGetText(SelectedElement).contains(SelectOptions)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public boolean VerifyDropDownSelectedItem(WebElement Element, String SelectOptions) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT);
            wait.until(ExpectedConditions.visibilityOf(Element));
            wait.until(ExpectedConditions.elementToBeClickable(Element));
            Select Dropdown = new Select(Element);
            WebElement SelectedElement = Dropdown.getFirstSelectedOption();
            if (ElementGetText(SelectedElement).contains(SelectOptions)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            EXTENT_TEST_LOGGER.log(LogStatus.FAIL, "did not selected: " + SelectOptions);
            return false;
        }
    }


    public String[][] ReadConfigData(String VarName) throws Exception {
        String[] stringCollection = config.getProperty(VarName).split(",");

        String data[][] = new String[1][stringCollection.length];
        for (int ForCntr = 0; ForCntr < stringCollection.length; ForCntr++) {
            data[1][ForCntr] = stringCollection[ForCntr];
        }
        return (data);
    }

    public void ChangeDriverUrl(String FromString, String ToString) throws Exception {
        String CurrentUrl = driver.getCurrentUrl();
        CurrentUrl = CurrentUrl.replace(FromString, ToString);
        driver.get(CurrentUrl);
        Thread.sleep(TIMEOUT_MS);
    }

    public boolean isDisplayed(final WebElement element) {
        return isDisplayed(element, 5);
    }

    public boolean isDisplayed(final By by) {
        return isDisplayed(by, 5);
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

    public boolean isDisplayed(final By by, final long timeOutInSeconds) {
        try {
            new WebDriverWait(driver, timeOutInSeconds).pollingEvery(200, TimeUnit.MILLISECONDS)
                    .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitUntilElementNotVisible(final By by) {
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitUntilElementNotVisible(final int timeOutInSeconds, final By by) {
        waitUntil(timeOutInSeconds, ExpectedConditions.invisibilityOfElementLocated(by));
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


}
