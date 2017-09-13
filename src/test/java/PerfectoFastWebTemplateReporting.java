import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.rmi.CORBA.Util;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Perfecto Desktop Web Using Selenium WebDriver:
 * This project demonstrate simply how to open a Desktop Web
 * machine within your Perfecto Lab in the cloud and running your tests
 * For more information regarding Perfecto Turbo Web solution please visit http://developers.perfectomobile.com/display/PD/Automating+Web-apps+with+Perfecto
 */

public class PerfectoFastWebTemplateReporting {
    private RemoteWebDriver driver;
    private ReportiumClient reportiumClient;

    @BeforeMethod
    void beforeMethod() throws MalformedURLException {

        // For more capabilities and supported platforms, see http://developers.perfectomobile.com/display/PD/Supported+Platforms
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");
        capabilities.setCapability("resolution", "1280x1024");
        capabilities.setCapability("securityToken", Utils.Capabilities.getToken());

        //For Regular web (physical devices) remove the /fast at the end of the Url
        URL url = new URL("https://" + Utils.Capabilities.getHost() + "/nexperience/perfectomobile/wd/hub/fast");

        driver = new RemoteWebDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        reportiumClient = Utils.DigitalZoom.initReportiumClient(driver);
    }

    @Test
    public void test() {
        System.out.println("Run started");

        // Complete your test here instead of the sample scenario
        reportiumClient.testStart("Turbo Web Template", new TestContext("Perfecto Turbo Web", "Tag 1", "Tag 2"));

         reportiumClient.stepStart("Step 1: Navigate to google");
        driver.get("http://google.com.com/");
         reportiumClient.stepEnd();
    }

    @AfterMethod
    void afterMethod(ITestResult result) {

        // Determine whether the test succeed or failed and report the result to DigitalZoom reporting
        if (result.getStatus() == ITestResult.SUCCESS) {
            reportiumClient.testStop(TestResultFactory.createSuccess());
        } else {
            Throwable throwable = result.getThrowable();
            reportiumClient.testStop(TestResultFactory.createFailure(throwable.getMessage(), throwable));
        }

        try {
            driver.close();
            driver.quit();

            // Retrieve the URL to the DigitalZoom Report
            String reportUrl = reportiumClient.getReportUrl();

            // Retrieve the URL to the Execution Summary PDF Report
            String reportPdfUrl = (String) (driver.getCapabilities().getCapability("reportPdfUrl"));

            System.out.println("report-url: " + reportUrl);
            System.out.println("report-pdf: " + reportPdfUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
