public class Menu {
    String nama;
    int harga;          // rupiah (tanpa titik)
    String kategori;    // "MAKANAN" atau "MINUMAN"

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori.toUpperCase();
    }

    public boolean isMinuman() {
        return "MINUMAN".equals(this.kategori);
    }

    public boolean isMakanan() {
        return "MAKANAN".equals(this.kategori);
    }
}
