import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by daniela on 5/8/17.
 */
public class Utils {

    static class Capabilities {

        private static final String token = "MyAuthToken";
        private static final String user = "MyUser";
        private static final String password = "MyPass";
        private static final String host = "MyHost.perfectomobile.com";

        public static String getToken() {
            return token;
        }

        public static String getUser() {
            return user;
        }

        public static String getPassword() {
            return password;
        }

        public static String getHost() {
            return host;
        }

    }

}
