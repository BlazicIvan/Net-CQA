package Examples.DemoExample.Library;

import Examples.DemoExample.Library.Release.Release;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Library implements Iterable<Release> {

    private List<Release> releases;

    public Library() {
        this.releases = new ArrayList<>();
    }

    public void addRelease(Release release) {
        releases.add(release);
    }

    public List<Release> getReleases(ReleaseFilter filter)
    {
        List<Release> result = null;
        if(filter == null) {
            result = releases;
        }
        else {
            result = filter.getResults(releases);
        }

        return result;
    }

    public int size() {
        return releases.size();
    }

    @Override
    public Iterator<Release> iterator() {
        return releases.iterator();
    }

    @Override
    public void forEach(Consumer<? super Release> action) {
        releases.forEach(action);
    }

    @Override
    public Spliterator<Release> spliterator() {
        return releases.spliterator();
    }
}
