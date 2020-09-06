package Examples.DemoExample.Library.Release;

import java.util.ArrayList;
import java.util.List;

public class Album extends AbstractRelease {

    private List<Song> songs;

    public Album(String name, String artist, int releaseDate) {
        super(name, artist, releaseDate);
        songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Song> getSongs() {
        return songs;
    }
}
