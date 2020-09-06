package Examples.DemoExample.Library.Release;

public abstract class AbstractRelease implements Release {

    String name;
    String artist;
    int releaseYear;

    public AbstractRelease(String name, String artist, int releaseYear) {
        this.name = name;
        this.artist = artist;
        this.releaseYear = releaseYear;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String getStringRepresentation() {
        return String.format("%s - %s (%d)", name, artist, releaseYear);
    }
}
