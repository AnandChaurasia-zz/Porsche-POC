import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.*;
import org.apache.commons.codec.binary.Base64;
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

            loginPage.setPorscheId(LoginPage.LoginCredentials.PORSCHE_ID.getLabel())
                    .setPassword(new String(Base64.decodeBase64(LoginPage.LoginCredentials.PASSWORT.getLabel())));

            MessagingPage messagingPage = loginPage.clickLoginButtonAndGoToMessagingPage().clickMessagingLink();

            NewMessagePage newMessagePage = messagingPage.clickOnNewMessage();

            softly.assertThat(newMessagePage.verifyStateOfSendButton("when all fields empty")).isFalse();

            newMessagePage.selectRecipient(MessagingData.RECIPIENT.getLabel());
            softly.assertThat(newMessagePage.getSelectRecipient().contentEquals(MessagingData.RECIPIENT.getLabel()));
            softly.assertThat(newMessagePage.verifyStateOfSendButton("after entering Recipient")).isFalse();

            newMessagePage.selectSubject(MessagingData.SUBJECT_MY_PORSCHE.getLabel());
            softly.assertThat(newMessagePage.getSelectRecipient().contentEquals(MessagingData.SUBJECT_MY_PORSCHE.getLabel()));
            softly.assertThat(newMessagePage.verifyStateOfSendButton("after entering Subject")).isFalse();

            newMessagePage.setMessageBody(MessagingData.BODY.getLabel());
            softly.assertThat(newMessagePage.verifyStateOfSendButton("after entering Body")).isTrue();

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
