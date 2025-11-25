/** Exception khusus saat item tidak ditemukan di menu */
public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
