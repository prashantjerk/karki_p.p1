//@author: Prashant Karki
// CS241

package songs;

public class ULItemNotFoundException extends RuntimeException {
    public ULItemNotFoundException() {
        super();
    }

    public ULItemNotFoundException(String message) {
        super(message);
    }
}
