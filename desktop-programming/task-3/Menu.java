import java.util.*;
import java.io.*;
import java.nio.file.*;

/** Manajer daftar menu (ArrayList<MenuItem>) + operasi I/O file */
public class Menu {
    private final List<MenuItem> items = new ArrayList<>();

    public void tambah(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> semua() {
        return Collections.unmodifiableList(items);
    }

    public List<Diskon> semuaDiskon() {
        List<Diskon> d = new ArrayList<>();
        for (MenuItem mi : items) if (mi instanceof Diskon) d.add((Diskon) mi);
        return d;
    }

    public MenuItem cariByNama(String nama) throws ItemNotFoundException {
        for (MenuItem mi : items) {
            if (mi.getNama().equalsIgnoreCase(nama)) return mi;
        }
        throw new ItemNotFoundException("Item \"" + nama + "\" tidak ditemukan di menu.");
    }

    /** Tampilkan tabel ringkas */
    public void tampilkan() {
        TextTable tt = new TextTable(new String[]{"Jenis", "Nama", "Kategori", "Harga/Info"});
        for (MenuItem mi : items) {
            String jenis;
            String info;
            if (mi instanceof Makanan) {
                jenis = "Makanan";
                info = ((Makanan) mi).getJenisMakanan() + " | " + rupiah(mi.getHarga());
            } else if (mi instanceof Minuman) {
                jenis = "Minuman";
                info = ((Minuman) mi).getJenisMinuman() + " | " + rupiah(mi.getHarga());
            } else if (mi instanceof Diskon) {
                jenis = "Diskon";
                Diskon d = (Diskon) mi;
                info = d.getPersenDiskon() + "% untuk " + d.getTargetKategori();
            } else {
                jenis = "Item";
                info = rupiah(mi.getHarga());
            }
            tt.addRow(new String[]{jenis, mi.getNama(), mi.getKategori(), info});
        }
        tt.print();
    }

    private String rupiah(double v) {
        return java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id","ID")).format(v);
    }

    /** Simpan ke menu.txt (CSV pakai titik-koma) */
    public void simpanKeFile(String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(path)))) {
            for (MenuItem mi : items) {
                if (mi instanceof Makanan) {
                    Makanan m = (Makanan) mi;
                    pw.printf("MAKANAN;%s;%s;%s;%s%n",
                            m.getNama(), m.getHarga(), m.getKategori(), m.getJenisMakanan());
                } else if (mi instanceof Minuman) {
                    Minuman m = (Minuman) mi;
                    pw.printf("MINUMAN;%s;%s;%s;%s%n",
                            m.getNama(), m.getHarga(), m.getKategori(), m.getJenisMinuman());
                } else if (mi instanceof Diskon) {
                    Diskon d = (Diskon) mi;
                    // Harga 0 sengaja (bukan item berbayar)
                    pw.printf("DISKON;%s;0;%s;%s%n",
                            d.getNama(), d.getTargetKategori(), d.getPersenDiskon());
                }
            }
        }
    }

    /** Muat dari menu.txt; baris invalid akan dilewati (robust terhadap error) */
    public void muatDariFile(String path) throws IOException {
        items.clear();
        Path p = Paths.get(path);
        if (!Files.exists(p)) return; // jika belum ada, biarkan kosong
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";");
                try {
                    String type = parts[0].trim().toUpperCase();
                    switch (type) {
                        case "MAKANAN": {
                            String nama = parts[1];
                            double harga = Double.parseDouble(parts[2]);
                            String kat = parts[3];
                            String jenis = parts[4];
                            tambah(new Makanan(nama, harga, kat, jenis));
                            break;
                        }
                        case "MINUMAN": {
                            String nama = parts[1];
                            double harga = Double.parseDouble(parts[2]);
                            String kat = parts[3];
                            String jenis = parts[4];
                            tambah(new Minuman(nama, harga, kat, jenis));
                            break;
                        }
                        case "DISKON": {
                            String nama = parts[1];
                            String targetKat = parts[3];
                            double persen = Double.parseDouble(parts[4]);
                            tambah(new Diskon(nama, targetKat, persen));
                            break;
                        }
                        default:
                            // abaikan baris tidak dikenal
                    }
                } catch (Exception e) {
                    // lewati baris yang tidak valid agar program tidak crash
                }
            }
        }
    }
}
