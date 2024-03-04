//@author: Prashant Karki
// CS241

package songs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Scanner;

public class ConsoleInterface {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("There must be two arguments.");
        } else {
            String songPath = args[0];
            Scanner fileInput = new Scanner(Path.of(songPath), StandardCharsets.UTF_8);

            Comparator<Song> cmp;
            if(Integer.parseInt(args[1]) == 0)
            {
                cmp = new NameComparator();
            } else if (Integer.parseInt(args[1]) == 1)
            {
                cmp = new AlbumComparator();
            } else if (Integer.parseInt(args[1]) == 2) {
                cmp = new ArtistComparator();
            } else {
                throw new IOException("EXCEPTION: The argument must be 0, 1 or 2.");
            }

            ULSortedList<Song> songList = new ULSortedList<>(cmp, 50);
            while(fileInput.hasNextLine())
            {
                String[] split = fileInput.nextLine().split(";");
                Song song = new Song(split[0], split[1], split[2]);
                songList.add(song);
            }

            for(int i = 0; i < songList.size(); i++)
            {
                System.out.println(songList.get(i));
            }
        }
    }
}
