package Examples.DemoExample.Persistency;
import Examples.DemoExample.Library.Library;

public interface StorageProvider {

    boolean saveLibrary(Library library);
    Library loadLibrary();

}
