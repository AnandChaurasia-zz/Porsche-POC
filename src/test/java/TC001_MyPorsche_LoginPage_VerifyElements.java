import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.LoginPage.LoginPageLabels;

import utils.Report;


public class TC001_MyPorsche_LoginPage_VerifyElements extends TestBase {
    private SoftAssertions softly = new SoftAssertions();
    private static LoginPage loginPage;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: Verify Elements on Login Page is loaded ")
    @Story("LOGIN")
    @Step("Start TC001_MyPorsche_LoginPage_VerifyElements")
    public void verifyElementsOfLoginPage() {
        EXTENT_REPORTS = Report.Instance("digitalCommunication");
        try {
            CreateExtentReport(this.getClass().getName(), "Verify elements of Login Page is loaded");
            loginPage = getAppLaunchPage();

            softly.assertThat(loginPage.verifyLoginAndRegisterButton()).isTrue();
            softly.assertThat(loginPage.getWelcomeMessage()).isEqualToIgnoringWhitespace(LoginPageLabels.WELCOME.getLabel());
            softly.assertThat(loginPage.getLoginWithOutPasswordApplicationNotification()).isEqualToIgnoringWhitespace(LoginPageLabels.WHAT_IS_LOGIN_WITHOUT_PASSWORD.getLabel());
            String notificationText=loginPage.getLoginWithOutPasswordApplicationNotification();
            EXTENT_TEST_LOGGER.log(LogStatus.INFO, "Popup info on tool tip for login without Password...: " + notificationText,EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));
        } catch (Exception exc) {
            EXTENT_TEST_LOGGER.log(LogStatus.ERROR, exc.getStackTrace().toString(), EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));

        }

    }

    @AfterClass
    void tearDown() {
        try {
            softly.assertAll();
            PostCondition();
        } catch (AssertionError Error) {
            EXTENT_TEST_LOGGER.log(LogStatus.ERROR, Error.getLocalizedMessage(), EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));
            PostConditionWithQuitDriver();
        }

    }


}
