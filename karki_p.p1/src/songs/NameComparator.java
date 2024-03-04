//@author: Prashant Karki
// CS241

package songs;

import java.util.Comparator;

public class NameComparator implements Comparator<Song> {
    @Override
    public int compare(Song song1, Song song2)
    {
        return song1.getName().compareTo(song2.getName());
    }
}
