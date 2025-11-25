/** Subkelas MenuItem: Makanan */
public class Makanan extends MenuItem {
    private String jenisMakanan; // contoh: "Nasi", "Gorengan", dll.

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenisMakanan) { this.jenisMakanan = jenisMakanan; }

    @Override
    public String tampilMenu() {
        return String.format("[Makanan] %s - %s (Jenis: %s)",
                getNama(), rupiah(getHarga()), jenisMakanan);
    }
}
