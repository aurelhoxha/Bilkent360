package aurelhoxha.bilkent360.other;

/**
 * Created by aurel on 6/20/17.
 */

public class User {
    private String displayName;
    private String displayEmail;

    public User()
    {

    }
    public User(String displayName, String displayEmail)
    {
        this.displayName  = displayName;
        this.displayEmail = displayEmail;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayEmail() {
        return displayEmail;
    }

    public void setDisplayEmail(String displayEmail) {
        this.displayEmail = displayEmail;
    }


}
