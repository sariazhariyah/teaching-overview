// Subkelas Makanan (Inheritance dari MenuItem)
public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    // Getter & Setter tambahan (Encapsulation)
    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    // Polymorphism: override tampilMenu()
    @Override
    public void tampilMenu() {
        System.out.println("[Makanan] " + getNama()
                + " | Jenis: " + jenisMakanan
                + " | Harga: " + formatRupiah(getHarga()));
    }
}
