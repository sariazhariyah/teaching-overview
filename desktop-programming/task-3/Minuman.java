// Subkelas Minuman (Inheritance dari MenuItem)
public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    // Getter & Setter tambahan (Encapsulation)
    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    // Polymorphism: override tampilMenu()
    @Override
    public void tampilMenu() {
        System.out.println("[Minuman] " + getNama()
                + " | Jenis: " + jenisMinuman
                + " | Harga: " + formatRupiah(getHarga()));
    }
}
