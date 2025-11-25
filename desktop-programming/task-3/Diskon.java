/** Subkelas MenuItem: Diskon
 *  Diskon persentase (0–100). Target kategori: "ALL", "Makanan", atau "Minuman".
 *  Harga diset 0 karena ini bukan item berharga, melainkan aturan diskon.
 */
public class Diskon extends MenuItem {
    private double persenDiskon;   // contoh: 10 artinya 10%
    private String targetKategori; // "ALL", "Makanan", "Minuman"

    public Diskon(String nama, String targetKategori, double persenDiskon) {
        super(nama, 0.0, targetKategori);
        this.persenDiskon = persenDiskon;
        this.targetKategori = targetKategori;
    }

    public double getPersenDiskon() { return persenDiskon; }
    public void setPersenDiskon(double p) { this.persenDiskon = p; }

    public String getTargetKategori() { return targetKategori; }
    public void setTargetKategori(String targetKategori) { this.targetKategori = targetKategori; }

    @Override
    public String tampilMenu() {
        return String.format("[Diskon] %s - %s%% untuk %s",
                getNama(), persenDiskon, targetKategori);
    }
}
