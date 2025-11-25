/** Subkelas MenuItem: Minuman */
public class Minuman extends MenuItem {
    private String jenisMinuman; // contoh: "Dingin", "Panas", "Jus", dll.

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() { return jenisMinuman; }
    public void setJenisMinuman(String jenisMinuman) { this.jenisMinuman = jenisMinuman; }

    @Override
    public String tampilMenu() {
        return String.format("[Minuman] %s - %s (Jenis: %s)",
                getNama(), rupiah(getHarga()), jenisMinuman);
    }
}
