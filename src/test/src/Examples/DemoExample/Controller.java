package Examples.DemoExample;

import Examples.DemoExample.Library.Library;
import Examples.DemoExample.Library.Release.Song;
import Examples.DemoExample.Persistency.CSVStorage;
import Examples.DemoExample.Persistency.StorageProvider;
import Examples.DemoExample.UI.UIController;
import Examples.DemoExample.UI.UIEventHandler;
import Examples.DemoExample.UI.UserInterface;

public class Controller implements UIEventHandler, Runnable {

    private UserInterface ui;
    private Library library;
    private StorageProvider storage;

    @Override
    public void onAddButtonClick() {
        library.addRelease(new Song(ui.getTitleInput(), ui.getArtistInput(), ui.getReleaseYear()));
    }

    @Override
    public void onListButtonClick() {
        ui.displayLibrary(library);
    }

    @Override
    public void onClose() {
        storage.saveLibrary(library);
    }

    @Override
    public void run() {
        storage = new CSVStorage("lib.csv");
        library = storage.loadLibrary();

        ui = new UIController();
        ui.registerEventHandler(this);
        ui.show();
    }
}
