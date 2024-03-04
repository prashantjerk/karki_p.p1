// @author Prashant Karki
// CS241

package songs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class MS1Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 1)
        {
            System.out.println("You have to one file you want to read from.");
            throw new IOException("Console interface does not have exactly one argument.");
        } else {
            String filePath = args[0];
            ArrayList<Song> songsArrayList = new ArrayList<>();
            Scanner fileInput = new Scanner(Path.of(filePath), StandardCharsets.UTF_8);

            while (fileInput.hasNextLine()) {   // checks whether there is a new line
                String[] split = fileInput.nextLine().split(";"); // returns current line and puts cursor to the next and splits on finding ';'
                Song song = new Song(split[0], split[1], split[2]);
                songsArrayList.add(song);
            }
            fileInput.close();
            // System.out.println("THE ARRAY LIST IS: " + songsArrayList.size());

            for (Song song : songsArrayList) {
                System.out.println(song);
            }
        }
    }
}