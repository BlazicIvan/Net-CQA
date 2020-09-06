package Examples.BadArchitecture.User;


import Examples.BadArchitecture.Data.DataStore;

public abstract class User {

    private String username;

    public User(String username) {
        this.username = username;
        DataStore.getInstance().add(this);
    }

    public final String getUsername() {
        return username;
    }
}
