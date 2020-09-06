package Examples.BadArchitecture;

import Examples.BadArchitecture.Data.DataStore;
import Examples.BadArchitecture.User.AdminUser;
import Examples.BadArchitecture.User.ReaderUser;
import Examples.BadArchitecture.User.User;
import Examples.BadArchitecture.Util.Util;

public class Main {

    public static void main(String args) {

        DataStore dataStore = DataStore.getInstance();

        for(int i = 0;i<10;i++) {
            dataStore.add(new ReaderUser("User" + Integer.toBinaryString(i)));
        }

        dataStore.add(new AdminUser("Adm01", "RWX"));

        for(User user : dataStore.getAll()) {
            Util.printLine(user.getUsername());
        }

    }

}
