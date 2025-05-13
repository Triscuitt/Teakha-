public class userSessionManager {
  private static String currentUser;
  private static String currentRole;

  public static void setCurrentUser(String username, String role) {

    currentUser = username;
    currentRole = role;

  }

  public static String getCurrentUser() {
    return currentUser;
  }
  public static String getCurrentRole(){
    return currentRole;
  }

}
