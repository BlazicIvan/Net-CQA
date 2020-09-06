package Examples.BadArchitecture.Data;

import Examples.BadArchitecture.User.User;
import Examples.BadArchitecture.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static DataStore instance = null;
    private ArrayList<User> users;
	
	public static String DB_FILE_NAME = "data.db";

    public static String getDefaultWorkingDir() {
        return "C:/";
    }

    private DataStore() {
        users = new ArrayList<>();
    }

    public static DataStore getInstance() {
        if(instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public User getByIndex(int index) {
        return users.get(index);
    }

    public void add(User user) {
        users.add(user);
        Util.printLine("User added.");
    }

    public List<User> getAll() {
        return users;
    }
}
