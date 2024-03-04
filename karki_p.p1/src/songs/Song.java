// @author Prashant Karki
// CS241

package songs;

// Every class implicitly extends Object class so no mention needed
public class Song implements Cloneable {
    private final String songName;
    private final String songAlbum;
    private final String songArtist;

    // Constructor for Song that takes a name, album, and artist.
    public Song(String name, String album, String artist)
    {
        songName = name;
        songAlbum = album;
        songArtist = artist;
    }

    // Creates a clone(copy) of this Song.
    // since we are only dealing with String type, we call just call super.clone
    // otherwise there would be need of deep cloning
    @Override
    public Song clone() throws CloneNotSupportedException {
        return (Song) super.clone();
    }

    // overriding equals in java.lang.Object
    @Override
    public boolean equals(Object otherSong)
    {
        Song castedSong = (Song) otherSong;
        return (this.songName.equals(castedSong.songName) && this.songAlbum.equals(castedSong.songAlbum)&& this.songArtist.equals(castedSong.songArtist));
    }

    // getter functions
    public String getAlbum()    {   return this.songAlbum;  }
    public String getName()     {   return this.songName;   }
    public String getArtist()   {   return this.songArtist; }

    // Should produce a string with the following format: name [album] - artist
    @Override
    public String toString()
    {
        return this.songName + " " + "[" + this.songAlbum + "]" + " - " + this.songArtist;
    }

}
