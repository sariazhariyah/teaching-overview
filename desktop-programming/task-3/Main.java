import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Menu menu = new Menu();
    private static final String MENU_FILE = "menu.txt";

    public static void main(String[] args) {
        // Muat menu awal (jika ada)
        try {
            menu.muatDariFile(MENU_FILE);
        } catch (IOException e) {
            System.out.println("Gagal memuat menu awal: " + e.getMessage());
        }

        // Jika kosong, isi contoh default agar mudah diuji
        if (menu.semua().isEmpty()) {
            seedContohMenu();
        }

        loopUtama();
    }

    private static void seedContohMenu() {
        menu.tambah(new Makanan("Nasi Goreng", 25000, "Makanan", "Nasi"));
        menu.tambah(new Makanan("Ayam Geprek", 28000, "Makanan", "Ayam"));
        menu.tambah(new Minuman("Es Teh", 8000, "Minuman", "Dingin"));
        menu.tambah(new Minuman("Kopi", 12000, "Minuman", "Panas"));
        menu.tambah(new Diskon("Promo Akhir Pekan", "ALL", 10));
        try { menu.simpanKeFile(MENU_FILE); } catch (IOException ignored) {}
    }

    private static void loopUtama() {
        while (true) {
            System.out.println("\n=== APLIKASI MANAJEMEN RESTORAN (TP3) ===");
            System.out.println("1. Tambah item menu");
            System.out.println("2. Tampilkan menu restoran");
            System.out.println("3. Buat pesanan baru");
            System.out.println("4. Simpan menu ke file");
            System.out.println("5. Muat menu dari file");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilih = sc.nextLine().trim();

            switch (pilih) {
                case "1": tambahItemMenu(); break;
                case "2": menu.tampilkan(); break;
                case "3": prosesPesanan(); break;
                case "4": simpanMenu(); break;
                case "5": muatMenu(); break;
                case "0":
                    System.out.println("Terima kasih. Keluar.");
                    return;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        }
    }

    private static void tambahItemMenu() {
        System.out.println("\nTambah Item:");
        System.out.println("1) Makanan  2) Minuman  3) Diskon");
        System.out.print("Pilih jenis: ");
        String jenis = sc.nextLine().trim();

        try {
            if (jenis.equals("1")) {
                System.out.print("Nama makanan: ");
                String nama = sc.nextLine();
                double harga = inputDouble("Harga: ");
                System.out.print("Jenis makanan (mis. Nasi, Ayam, dll): ");
                String jm = sc.nextLine();
                menu.tambah(new Makanan(nama, harga, "Makanan", jm));
                System.out.println("-> Makanan ditambahkan.");
            } else if (jenis.equals("2")) {
                System.out.print("Nama minuman: ");
                String nama = sc.nextLine();
                double harga = inputDouble("Harga: ");
                System.out.print("Jenis minuman (mis. Dingin, Panas, Jus): ");
                String jn = sc.nextLine();
                menu.tambah(new Minuman(nama, harga, "Minuman", jn));
                System.out.println("-> Minuman ditambahkan.");
            } else if (jenis.equals("3")) {
                System.out.print("Nama diskon: ");
                String nama = sc.nextLine();
                System.out.print("Target kategori (ALL/Makanan/Minuman): ");
                String target = sc.nextLine().trim();
                double p = inputDouble("Persen diskon (0-100): ");
                if (p < 0 || p > 100) {
                    System.out.println("Persen diskon invalid.");
                    return;
                }
                menu.tambah(new Diskon(nama, target, p));
                System.out.println("-> Diskon ditambahkan.");
            } else {
                System.out.println("Jenis tidak dikenal.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Gagal menambah item: " + e.getMessage());
        }
    }

    private static void prosesPesanan() {
        Pesanan pesanan = new Pesanan();
        System.out.println("\n== Buat Pesanan ==");
        while (true) {
            System.out.println("a) Tambah item by nama");
            System.out.println("b) Lihat menu");
            System.out.println("c) Selesai & hitung total");
            System.out.print("Pilih: ");
            String p = sc.nextLine().trim();
            if ("b".equalsIgnoreCase(p)) {
                menu.tampilkan();
            } else if ("a".equalsIgnoreCase(p)) {
                try {
                    System.out.print("Masukkan nama item persis (case-insensitive): ");
                    String nm = sc.nextLine();
                    MenuItem mi = menu.cariByNama(nm); // bisa lempar ItemNotFoundException
                    int qty = (int) inputDouble("Qty: ");
                    if (qty <= 0) {
                        System.out.println("Qty harus > 0.");
                        continue;
                    }
                    if (mi instanceof Diskon) {
                        System.out.println("Diskon bukan item yang bisa dipesan. Diskon diterapkan otomatis saat hitung total.");
                        continue;
                    }
                    pesanan.tambah(mi, qty);
                    System.out.println("-> Ditambahkan.");
                } catch (ItemNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Input invalid.");
                }
            } else if ("c".equalsIgnoreCase(p)) {
                break;
            } else {
                System.out.println("Pilihan tidak dikenal.");
            }
        }

        // Hitung total dengan diskon yang ada di daftar menu
        List<Diskon> diskonTerpakai = menu.semuaDiskon();
        double subtotal = pesanan.hitungSubtotal();
        double totalAkhir = pesanan.hitungTotalDenganDiskon(diskonTerpakai);

        System.out.println("\n== RINGKASAN PESANAN ==");
        for (OrderLine ol : pesanan.getLines()) {
            System.out.printf("- %s x%d = Rp%,.0f%n",
                    ol.getItem().getNama(), ol.getQty(), ol.getSubTotal());
        }
        System.out.printf("Subtotal: Rp%,.0f%n", subtotal);
        if (!diskonTerpakai.isEmpty()) {
            System.out.println("Diskon:");
            for (Diskon d : diskonTerpakai) {
                System.out.printf("  * %s (%s%% untuk %s)%n",
                        d.getNama(), d.getPersenDiskon(), d.getTargetKategori());
            }
        } else {
            System.out.println("Diskon: -");
        }
        System.out.printf("TOTAL: Rp%,.0f%n", totalAkhir);

        // Simpan struk
        try {
            var path = pesanan.simpanStrukKeFile(diskonTerpakai, totalAkhir);
            System.out.println("Struk tersimpan: " + path.toString());
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }

    private static void simpanMenu() {
        try {
            menu.simpanKeFile(MENU_FILE);
            System.out.println("Menu disimpan ke " + MENU_FILE);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan: " + e.getMessage());
        }
    }

    private static void muatMenu() {
        try {
            menu.muatDariFile(MENU_FILE);
            System.out.println("Menu dimuat dari " + MENU_FILE);
        } catch (IOException e) {
            System.out.println("Gagal memuat: " + e.getMessage());
        }
    }

    private static double inputDouble(String label) {
        while (true) {
            try {
                System.out.print(label);
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Masukan angka yang valid.");
            }
        }
    }
}
