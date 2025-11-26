import java.util.ArrayList;
import java.util.List;
import java.io.*;

// Kelas Menu menyimpan semua item menu
public class Menu {
    private List<MenuItem> daftarMenu = new ArrayList<>();

    public Menu() {
    }

    // Getter & Setter (Encapsulation)
    public List<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public void setDaftarMenu(List<MenuItem> daftarMenuBaru) {
        this.daftarMenu = daftarMenuBaru;
    }

    public void tambahMenu(MenuItem item) {
        daftarMenu.add(item);
    }

    public int getJumlahMenu() {
        return daftarMenu.size();
    }

    public void tampilMenu() {
        System.out.println("=== DAFTAR MENU RESTORAN ===");
        if (daftarMenu.isEmpty()) {
            System.out.println("(Belum ada menu)");
        } else {
            int no = 1;
            for (MenuItem item : daftarMenu) {
                System.out.print(no + ". ");
                item.tampilMenu(); // Polymorphism
                no++;
            }
        }
        System.out.println("============================");
    }

    // Mengambil item berdasarkan index dengan Exception khusus
    public MenuItem getMenuItem(int index) throws ItemNotFoundException {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new ItemNotFoundException("Item dengan nomor " + (index + 1) + " tidak ditemukan.");
        }
        return daftarMenu.get(index);
    }

    // Simpan daftar menu ke file teks
    // Format tiap baris:
    // Makanan,nama,harga,jenisMakanan
    // Minuman,nama,harga,jenisMinuman
    // Diskon,nama,persentaseDiskon
    public void simpanMenuKeFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (MenuItem item : daftarMenu) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    bw.write("Makanan," + m.getNama() + "," + m.getHarga() + "," + m.getJenisMakanan());
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    bw.write("Minuman," + m.getNama() + "," + m.getHarga() + "," + m.getJenisMinuman());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    bw.write("Diskon," + d.getNama() + "," + d.getPersentaseDiskon());
                }
                bw.newLine();
            }
        }
    }

    // Memuat menu dari file teks (sesuai format di atas)
    public void muatMenuDariFile(String filename) throws IOException {
        daftarMenu.clear();
        File file = new File(filename);
        if (!file.exists()) {
            // Jika file belum ada, tidak dianggap error fatal
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String tipe = data[0];
                switch (tipe) {
                    case "Makanan":
                        // Makanan,nama,harga,jenisMakanan
                        tambahMenu(new Makanan(
                                data[1],
                                Double.parseDouble(data[2]),
                                data[3]
                        ));
                        break;
                    case "Minuman":
                        // Minuman,nama,harga,jenisMinuman
                        tambahMenu(new Minuman(
                                data[1],
                                Double.parseDouble(data[2]),
                                data[3]
                        ));
                        break;
                    case "Diskon":
                        // Diskon,nama,persentaseDiskon
                        tambahMenu(new Diskon(
                                data[1],
                                Double.parseDouble(data[2])
                        ));
                        break;
                }
            }
        }
    }
}
