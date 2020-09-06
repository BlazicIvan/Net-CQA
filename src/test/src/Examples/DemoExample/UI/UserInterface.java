package Examples.DemoExample.UI;

import Examples.DemoExample.Library.Library;

public interface UserInterface {

    String getTitleInput();

    String getArtistInput();

    int getReleaseYear();

    void displayLibrary(Library library);

    void registerEventHandler(UIEventHandler eventHandler);

    void show();

}
