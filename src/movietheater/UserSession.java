package movietheater;

public final class UserSession {
    private static UserSession instance;

    private String username;
    private int userID;

    private UserSession(String username, int userID) {
        this.username = username;
        this.userID = userID;
    }

    public static UserSession getInstance(String username, int userID) {
        if (instance == null) {
            instance = new UserSession(username, userID);
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void cleanUserSession() {
        username = "";
        userID = 0;
        instance = null;
    }

    @Override
    public String toString() {
        return "UserSession{userID= "+userID+", username= "+username+"}";
    }
}
