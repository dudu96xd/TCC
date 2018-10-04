package grupo9.usjt.usjt.com.helper.utils;

import com.facebook.AccessToken;

public class LoginHelper {

    public static String ID_USER;
    public static String EMAIL;

    public static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
