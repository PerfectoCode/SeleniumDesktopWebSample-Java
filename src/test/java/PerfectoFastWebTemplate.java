import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Perfecto Desktop Web Using Selenium WebDriver:
 * This project demonstrate simply how to open a Desktop Web
 * machine within your Perfecto Lab in the cloud and running your tests
 *
 * The project uses Perfecto Turbo Web, for more information follow the instructions at:
 * http://developers.perfectomobile.com/display/PD/Turbo+Web+Automation
 */
public class PerfectoFastWebTemplate {

    private RemoteWebDriver driver;

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

        // For Perfecto Turbo Web solution follow the instructions at http://developers.perfectomobile.com/display/PD/Turbo+Web+Automation
        URL url = new URL("https://" + Utils.Capabilities.getHost() + "/nexperience/perfectomobile/wd/hub/fast");

        driver = new RemoteWebDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    }

    @Test
    public void test() {
        System.out.println("Run started");

        driver.get("http://google.com.com/");

        // complete your test here
    }

    @AfterMethod
    void afterMethod(ITestResult result) {
        driver.close();
        driver.quit();
    }


}
