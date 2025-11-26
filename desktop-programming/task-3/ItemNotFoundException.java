// Contoh custom exception untuk memenuhi kriteria Exception
public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
