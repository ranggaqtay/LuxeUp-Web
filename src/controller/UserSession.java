package Controller;

public class UserSession {
    private static String loggedInUser;

    public static void setLoggedInUser(String username) {
        loggedInUser = username;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }
}