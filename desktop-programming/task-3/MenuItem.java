import java.text.NumberFormat;
import java.util.Locale;

/** Abstraksi item menu (basis untuk makanan, minuman, diskon) */
public abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori; // misal: "Makanan", "Minuman", atau target untuk Diskon

    protected MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    /** Polymorphism: setiap turunan menampilkan informasi sesuai jenisnya */
    public abstract String tampilMenu();

    protected String rupiah(double v) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return nf.format(v);
    }
}

