package pages;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import helper.SeleniumHelper;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface IPage {
    // Returns true if application is in the page requested
    boolean verifyPageIsLoaded() throws Exception;
}
