import java.util.ArrayList;
import java.util.List;
import java.io.*;

// Kelas Pesanan menyimpan item-item yang dipesan pelanggan
public class Pesanan {
    private List<MenuItem> daftarPesanan = new ArrayList<>();
    private double totalHarga;
    private double diskonPersen;

    public Pesanan() {
    }

    // Getter & Setter (Encapsulation)
    public List<MenuItem> getDaftarPesanan() {
        return daftarPesanan;
    }

    public void setDaftarPesanan(List<MenuItem> daftarPesanan) {
        this.daftarPesanan = daftarPesanan;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public double getDiskonPersen() {
        return diskonPersen;
    }

    public void setDiskonPersen(double diskonPersen) {
        this.diskonPersen = diskonPersen;
    }

    public void tambahPesanan(MenuItem item) {
        daftarPesanan.add(item);
    }

    // Menghitung total, memperhitungkan Diskon (jika ada)
    public void hitungTotal() {
        totalHarga = 0;
        diskonPersen = 0;

        for (MenuItem item : daftarPesanan) {
            if (item instanceof Diskon) {
                diskonPersen = ((Diskon) item).getPersentaseDiskon();
            } else {
                totalHarga += item.getHarga();
            }
        }

        if (diskonPersen > 0) {
            totalHarga = totalHarga - (totalHarga * diskonPersen / 100.0);
        }
    }

    public void tampilStruk() {
        System.out.println("======= STRUK PESANAN (OBJEK) =======");
        if (daftarPesanan.isEmpty()) {
            System.out.println("(Belum ada pesanan)");
        } else {
            for (MenuItem item : daftarPesanan) {
                item.tampilMenu();
            }
            System.out.println("-------------------------------------");
            System.out.println("Diskon: " + diskonPersen + "%");
            System.out.println("Total Bayar: " + totalHarga);
        }
        System.out.println("=====================================");
    }

    // Menyimpan struk ke file teks sederhana
    public void simpanStrukKeFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("STRUK PESANAN\n");
            for (MenuItem item : daftarPesanan) {
                if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    bw.write("[Diskon] " + d.getNama() + " - " + d.getPersentaseDiskon() + "%\n");
                } else {
                    bw.write(item.getKategori() + " - " + item.getNama() + " - " + item.getHarga() + "\n");
                }
            }
            bw.write("Diskon: " + diskonPersen + "%\n");
            bw.write("Total: " + totalHarga + "\n");
        }
    }

    // Membaca struk dari file teks dan menampilkannya ke layar
    public void muatStrukDariFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File struk belum ada.");
            return;
        }

        System.out.println("======= STRUK PESANAN (DARI FILE) =======");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("=========================================");
    }
}
