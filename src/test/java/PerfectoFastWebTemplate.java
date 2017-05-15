import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Perfecto Web Automation Code Sample
 */
public class PerfectoFastWebTemplate {

    private RemoteWebDriver driver;
    private ReportiumClient reportiumClient;

    @BeforeMethod
    void beforeMethod() throws MalformedURLException {

        // For more capabilities and supported platforms, see http://developers.perfectomobile.com/display/PD/Supported+Platforms
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "58");
        capabilities.setCapability("resolution", "1280x1024");
        capabilities.setCapability("user", Utils.Capabilities.getUser());
        capabilities.setCapability("password", Utils.Capabilities.getPassword());

        URL url = new URL("https://" + Utils.Capabilities.getHost() + "/nexperience/perfectomobile/wd/hub");

        // For Perfecto Turbo Web solution follow the instructions at http://developers.perfectomobile.com/display/PD/Turbo+Web+Automation
        // You may want to enable the following lines of code to enable Turbo Web:
        // url = new URL(url, "/fast");
        // capabilities.setCapability("securityToken", Utils.Capabilities.getToken());

        driver = new RemoteWebDriver(url, capabilities);
        reportiumClient = initReportiumClient();

    }

    @Test
    public void test() {
        System.out.println("Run started");

        // Complete your test here instead of the sample scenario

        /** Sample Scenario **/
        reportiumClient.testStart("Turbo Web Sample", new TestContext("Perfecto Turbo Web", "Tag 1", "Tag 2"));

        reportiumClient.stepStart("Step 1: Navigate to developers.perfectomobile.com");
        driver.get("http://developers.perfectomobile.com/");
        reportiumClient.stepEnd();

        reportiumClient.stepStart("Step 2: Search for \"Turbo Web\" documentation");
        String searchValue = "Turbo Web";
        WebElement searchBox = driver.findElement(By.id("quick-search-query"));
        searchBox.sendKeys(searchValue);
        // comparison between actual and expected string value
        boolean isEqual = searchBox.getAttribute("value").compareTo(searchValue) == 0;
        reportiumClient.reportiumAssert("Validate search value: " + searchValue, isEqual);
        reportiumClient.stepEnd();

        reportiumClient.stepStart("Step 3: Click the Turbo Web Automation search result");
        driver.findElement(By.xpath("//*[contains(text(), 'Turbo Web Automation')]")).click();
        reportiumClient.stepEnd();

        reportiumClient.stepStart("Step 3: validate 4 steps appear in the page");
        String toFind = "//*[contains(text(), 'Step %s:')]";
        for (int i = 1; i < 5; i++) {
            boolean isPresented = driver.findElements(By.xpath(String.format(toFind, i))).size() > 0;
            reportiumClient.reportiumAssert(String.format("Element with text \"Step %s\"", i), isPresented);
        }
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

    /**
     * Create a reportium client instance
     * For more information about Perfecto DigitalZoom reporting, see http://developers.perfectomobile.com/display/PD/Reporting
     *
     * @return ReportiumClient
     */
    private ReportiumClient initReportiumClient() {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Turbo Web", "1"))
                .withJob(new Job("Job Name", 1))
                .withContextTags("Turbo Web", "Web", "Selenium")
                .withWebDriver(driver)
                .build();

        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

}
