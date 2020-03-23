import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MessagingPage;
import utils.Report;

public class TC003_MyPorsche_LoginPage_LoginToPorscheInBox extends TestBase {
    private SoftAssertions softly = new SoftAssertions();
    private static LoginPage loginPage;
    private static MessagingPage messagingPage;


    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: Login test with correct username and password")
    @Story("Valid Username and Password test")
    @Step("Start TC003_MyPorsche_LoginPage_LoginToPorscheInBoxWithValidCredential")
    public void loginToPorscheInBoxWithValidUserAndPassword() {
        EXTENT_REPORTS = Report.Instance("digitalCommunication");
        try {
            CreateExtentReport(this.getClass().getName(), "Login to My Porsche Web application");
            loginPage = getAppLaunchPage();
            //softly.assertThat(loginPage.verifyPageIsLoaded()).isTrue();


            loginPage.setPorscheId(LoginPage.LoginCredentials.PORSCHE_ID2.getLabel())
                    .setPassword(LoginPage.LoginCredentials.PASSWORT2.getLabel());

            messagingPage = loginPage.clickLoginButtonAndGoToMessagingPage();

            messagingPage.clickOnUserLoggedInIcon().clickOnLogOutButton();
            EXTENT_TEST_LOGGER.log(LogStatus.PASS, "Log Out button is clicked", EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));

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
