import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MessagingPage;
import pages.NewMessagePage;
import pages.NewMessagePage.MessagingData;
import utils.Report;


public class TC004_MyPorsche_MessagingPage_VerifyAllFieldsAreMandatoryToSendMessage extends TestBase {
    private SoftAssertions softly = new SoftAssertions();
    private static LoginPage loginPage;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: To verify all the fields(Recipient,Subject and Body) for sending the message are mandatory")
    @Story("Messaging feature")
    @Step("Start TC004_MyPorsche_MessagingPage_VerifyAllFieldsAreMandatoryToSendMessage")

    public void VerifyAllFieldsAreMandatoryForSendingMessage() {
        EXTENT_REPORTS = Report.Instance("digitalCommunication");
        try {
            CreateExtentReport(this.getClass().getName(), "To verify all the fields(Recipient,Subject and Body) for sending the message are mandatory");
            loginPage = getAppLaunchPage();

            loginPage.setPorscheId(LoginPage.LoginCredentials.PORSCHE_ID2.getLabel())
                    .setPassword(LoginPage.LoginCredentials.PASSWORT2.getLabel());

            MessagingPage messagingPage = loginPage.clickLoginButtonAndGoToMessagingPage().clickMessagingLink();

            NewMessagePage newMessagePage = messagingPage.clickOnNewMessage();

            softly.assertThat(newMessagePage.verifyStateOfSendButton()).isFalse();

            newMessagePage.setRecipient(MessagingData.RECIPIENT.getLabel());
            softly.assertThat(newMessagePage.verifyStateOfSendButton()).isFalse();

            newMessagePage.setMessageSubject(MessagingData.SUBJECT.getLabel());
            softly.assertThat(newMessagePage.verifyStateOfSendButton()).isFalse();

            newMessagePage.setMessageBody(MessagingData.BODY.getLabel());
            softly.assertThat(newMessagePage.verifyStateOfSendButton()).isTrue();

            newMessagePage.clickOnCancelButton();
            messagingPage.clickOnUserLoggedInIcon().clickOnLogOutButton();
            EXTENT_TEST_LOGGER.log(LogStatus.PASS, "Log Out button is clicked", EXTENT_TEST_LOGGER.addScreenCapture(Report.CaptureScreen(driver)));


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
