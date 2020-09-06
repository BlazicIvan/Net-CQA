package Examples.DemoExample.Library;

import Examples.DemoExample.Library.Release.Release;

import java.util.List;

public interface ReleaseFilter {

    List<Release> getResults(List<Release> releases);
}
