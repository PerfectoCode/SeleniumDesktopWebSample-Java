import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Utils class
 */
public class Utils {

    static class Capabilities {

        private static final String token = System.getenv("token");
        private static final String host = System.getenv("host");

        public static String getToken() {
            return token;
        }

        public static String getHost() {
            return host;
        }
    }

    static class DigitalZoom {

        /**
         * Create a reportium client instance
         * For more information about Perfecto DigitalZoom reporting, see http://developers.perfectomobile.com/display/PD/Reporting
         *
         * @return ReportiumClient
         */

        public static ReportiumClient initReportiumClient(RemoteWebDriver driver) {
            PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                    .withProject(new Project("Turbo Web", "1"))
                    .withJob(new Job("Job Name", 1))
                    .withContextTags("Turbo Web", "Web", "Selenium")
                    .withWebDriver(driver)
                    .build();

            return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
        }
    }
}
