package Examples.BadArchitecture.User;

public class AdminUser extends User {

    private String permissions;

    public AdminUser(String username, String permissions) {
        super(username);
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }
}
