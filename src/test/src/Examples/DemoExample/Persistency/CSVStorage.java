package Examples.DemoExample.Persistency;

import Examples.DemoExample.Library.Library;
import Examples.DemoExample.Library.Release.Release;
import Examples.DemoExample.Library.Release.Song;

import java.io.*;

public class CSVStorage implements StorageProvider {

    private String fileName;

    private File getStorageFile() throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    public CSVStorage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean saveLibrary(Library library) {
        try {
            FileOutputStream outputStream = new FileOutputStream(getStorageFile());

            for(Release release : library) {
                outputStream.write((release.getName() + ",").getBytes());
                outputStream.write((release.getArtist() + ",").getBytes());
                outputStream.write((release.getReleaseYear() + "\n").getBytes());
            }

            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public Library loadLibrary() {
        Library library = new Library();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getStorageFile()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                Release release = new Song(parts[0], parts[1], Integer.parseInt(parts[2]));
                library.addRelease(release);
            }

            bufferedReader.close();
        } catch (IOException e) {}

        return library;
    }
}
