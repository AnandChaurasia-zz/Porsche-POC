import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;


import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.LoginPage.LoginPageLabels;
import utils.Report;

public class TC002_MyPorsche_LoginPage_LoginToPorscheInBoxWithInvalidCredential extends TestBase {
    private SoftAssertions softly = new SoftAssertions();
    private static LoginPage loginPage;
    private static final Logger LOGGER =  LogManager.getLogger(TC002_MyPorsche_LoginPage_LoginToPorscheInBoxWithInvalidCredential.class);

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Login test with invalid username and password")
    @Story("Valid Username and Password test")
    @Step("Start TC002_MyPorsche_LoginPage_LoginToPorscheInBoxWithInvalidCredential")
    public void loginToPorscheInBoxWithInValidUserAndPassword() {
        EXTENT_REPORTS = Report.Instance("digitalCommunication");
        try {
            CreateExtentReport( this.getClass().getName(), "Login to My Porsche Web application");
            loginPage = getAppLaunchPage();

            loginPage.setPorscheId(LoginPage.LoginCredentials.PORSCHE_ID.getLabel())
                    .setPassword(LoginPage.LoginCredentials.PASSWORT.getLabel()).clickLoginButton();

            softly.assertThat(loginPage.getInvalidLoginDetailsNotification()).isEqualToIgnoringWhitespace(LoginPageLabels.INVALID_CREDENTIAL.getLabel());
            EXTENT_TEST_LOGGER.log(LogStatus.PASS, "Login data is Invalid", EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));

        } catch (Exception exc) {
            EXTENT_TEST_LOGGER.log(LogStatus.ERROR,exc.getStackTrace().toString() ,EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));

        }

    }

    @AfterClass
    void tearDown(){
        try {
            softly.assertAll();
            PostCondition();
        }catch (AssertionError Error){
            EXTENT_TEST_LOGGER.log(LogStatus.ERROR,Error.getLocalizedMessage() , EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));
            PostConditionWithQuitDriver();
        }

    }

}
