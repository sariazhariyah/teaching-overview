import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    // ====== KONFIG ======
    static final int MAX_MENU = 100;
    static final int MAX_PESANAN = 200;
    static final int BIAYA_SERVICE = 20000;
    static final double PAJAK_PERSEN = 0.10;

    // ====== STATE ======
    static Scanner sc = new Scanner(System.in);
    static NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    // Array menu (kapasitas tetap)
    static Menu[] menu = new Menu[MAX_MENU];

    // Keranjang pesanan
    static OrderLine[] keranjang = new OrderLine[MAX_PESANAN];
    static int jumlahItemPesanan = 0;

    public static void main(String[] args) {
        initSampleMenu();

        do {
            tampilMenuUtama();
            int pilih = inputInt("Pilih: ");
            switch (pilih) {
                case 1: menuPelanggan(); break;
                case 2: menuAdmin(); break;
                case 3: System.out.println("Terima kasih. Sampai jumpa!"); return;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (true);
    }

    // ====== Inisialisasi awal ======
    static void initSampleMenu() {
        // 4 makanan + 4 minuman (bisa diedit via Admin)
        menu[0] = new Menu("Nasi Goreng", 25000, "MAKANAN");
        menu[1] = new Menu("Ayam Geprek", 28000, "MAKANAN");
        menu[2] = new Menu("Sate Ayam", 30000, "MAKANAN");
        menu[3] = new Menu("Rendang", 35000, "MAKANAN");
        menu[4] = new Menu("Es Teh", 8000, "MINUMAN");
        menu[5] = new Menu("Kopi", 12000, "MINUMAN");
        menu[6] = new Menu("Jus Alpukat", 18000, "MINUMAN");
        menu[7] = new Menu("Air Mineral", 5000, "MINUMAN");
    }

    // ====== Menu Utama ======
    static void tampilMenuUtama() {
        System.out.println("\n=== RESTO CLI ===zxzxzx");
        System.out.println("1) Pelanggan (Pemesanan)");
        System.out.println("2) Admin (Pengelolaan Menu)");
        System.out.println("3) Keluar");
    }

    // ====== Pelanggan ======
    static void menuPelanggan() {
        resetKeranjang();
        tampilDaftarMenuKelompok();

        System.out.println("\nKetik nama menu berulang (atau 'selesai' untuk selesai):");
        do { // do-while -> minimal sekali jalan
            String nama = inputString("> ");
            if (nama.equalsIgnoreCase("selesai")) break;

            Menu m = cariMenuByNama(nama);
            if (m == null) {
                System.out.println("Menu tidak ditemukan. Ketik ulang sesuai daftar.");
                continue;
            }

            int qty = inputInt("Jumlah: ");
            if (qty <= 0) {
                System.out.println("Jumlah harus > 0.");
                continue;
            }
            tambahKeKeranjang(m, qty);
        } while (true);

        if (jumlahItemPesanan == 0) {
            System.out.println("Tidak ada pesanan.");
            return;
        }

        // Promo BOGO dicek dari subtotal sebelum BOGO
        terapkanPromoBogoJikaSyarat();

        // Cetak struk
        cetakStruk();
    }

    // ====== Admin ======
    static void menuAdmin() {
        do {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1) Tampilkan Menu (kelompok)");
            System.out.println("2) Tambah Menu (bisa beberapa sekaligus)");
            System.out.println("3) Ubah Harga");
            System.out.println("4) Hapus Menu");
            System.out.println("5) Kembali");
            int p = inputInt("Pilih: ");

            switch (p) {
                case 1: tampilDaftarMenuKelompok(); break;
                case 2: adminTambahMenuBanyak(); break;
                case 3: adminUbahHarga(); break;
                case 4: adminHapusMenu(); break;
                case 5: return;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (true);
    }

    // ====== Tampil Menu (kelompok per kategori) ======
    static void tampilDaftarMenuKelompok() {
        System.out.println("\n-- DAFTAR MENU --");
        System.out.println("[MAKANAN]");
        int no = 1;
        for (Menu m : menu) {
            if (m != null && m.isMakanan()) {
                System.out.printf("%2d. %-20s %s%n", no, m.nama, rupiah.format(m.harga));
            }
            no++;
        }
        System.out.println("[MINUMAN]");
        no = 1;
        for (Menu m : menu) {
            if (m != null && m.isMinuman()) {
                System.out.printf("%2d. %-20s %s%n", no, m.nama, rupiah.format(m.harga));
            }
            no++;
        }
    }

    // ====== Pencarian Menu ======
    static Menu cariMenuByNama(String nama) {
        for (Menu m : menu) {
            if (m != null && m.nama.equalsIgnoreCase(nama)) return m;
        }
        return null;
    }

    // ====== Keranjang ======
    static void resetKeranjang() {
        for (int i = 0; i < keranjang.length; i++) keranjang[i] = null;
        jumlahItemPesanan = 0;
    }

    static void tambahKeKeranjang(Menu m, int qty) {
        // Gabungkan item yang sama
        for (int i = 0; i < jumlahItemPesanan; i++) {
            if (keranjang[i].item.nama.equalsIgnoreCase(m.nama)) {
                keranjang[i].qty += qty;
                return;
            }
        }
        keranjang[jumlahItemPesanan++] = new OrderLine(m, qty);
    }

    // ====== Promo & Perhitungan ======
    static int subtotalSebelumBogo() {
        int sum = 0;
        for (int i = 0; i < jumlahItemPesanan; i++) {
            sum += keranjang[i].totalSebelumBogo();
        }
        return sum;
    }

    static int subtotalSetelahBogo() {
        int sum = 0;
        for (int i = 0; i < jumlahItemPesanan; i++) {
            sum += keranjang[i].totalSetelahBogo();
        }
        return sum;
    }

    static void terapkanPromoBogoJikaSyarat() {
        int subAwal = subtotalSebelumBogo();
        if (subAwal > 50000) { // syarat BOGO minuman
            for (int i = 0; i < jumlahItemPesanan; i++) {
                OrderLine ol = keranjang[i];
                if (ol.item.isMinuman()) {
                    ol.gratisBogo = ol.qty / 2; // floor
                }
            }
        }
    }

    static void cetakStruk() {
        System.out.println("\n[STRUK PEMESANAN]");
        for (int i = 0; i < jumlahItemPesanan; i++) {
            OrderLine ol = keranjang[i];
            String line = String.format("%-20s x%-2d @%s = %s",
                    ol.item.nama, ol.qty, rupiah.format(ol.item.harga), rupiah.format(ol.totalSetelahBogo()));
            if (ol.item.isMinuman() && ol.gratisBogo > 0) {
                line += String.format("  (BOGO: gratis %d)", ol.gratisBogo);
            }
            System.out.println(line);
        }
        System.out.println("-----------------------------------------");

        int subAfterBogo = subtotalSetelahBogo();
        int diskon10 = (subAfterBogo > 100000) ? (subAfterBogo / 10) : 0;
        int dpp = subAfterBogo - diskon10;
        int pajak = (int)Math.round(dpp * PAJAK_PERSEN);
        int total = dpp + pajak + BIAYA_SERVICE;

        System.out.printf("%-24s : %s%n", "Subtotal (setelah BOGO)", rupiah.format(subAfterBogo));
        System.out.printf("%-24s : %s%n", "Diskon 10% (>100k)", rupiah.format(diskon10));
        System.out.printf("%-24s : %s%n", "Pajak 10%", rupiah.format(pajak));
        System.out.printf("%-24s : %s%n", "Biaya Pelayanan", rupiah.format(BIAYA_SERVICE));
        System.out.printf("%-24s : %s%n", "TOTAL", rupiah.format(total));

        // Info tambahan promo (nested if contoh)
        int subAwal = subtotalSebelumBogo();
        if (subAwal > 50000) {
            System.out.println("Catatan: Promo Beli 1 Gratis 1 (kategori MINUMAN) aktif.");
        } else if (subAfterBogo > 100000) {
            System.out.println("Catatan: Diskon 10% aktif (subtotal setelah BOGO > 100k).");
        } else {
            System.out.println("Catatan: Tidak ada promo yang aktif.");
        }
    }

    // ====== Admin: Tambah/Ubah/Hapus ======
    static void adminTambahMenuBanyak() {
        System.out.println("\nTambahkan menu baru (ketik 'selesai' pada nama untuk berhenti).");
        while (true) { // gunakan while
            String nama = inputString("Nama menu baru: ");
            if (nama.equalsIgnoreCase("selesai")) break;

            int harga = inputInt("Harga (angka): ");
            String kategori = inputKategori("Kategori (MAKANAN/MINUMAN): ");

            int idxKosong = cariSlotKosongMenu();
            if (idxKosong == -1) {
                System.out.println("Kapasitas menu penuh.");
                return;
            }

            String konfirm = inputString("Yakin tambah? (Ya/Tidak): ").trim();
            if (konfirm.equalsIgnoreCase("Ya")) {
                menu[idxKosong] = new Menu(nama, harga, kategori);
                System.out.println("-> Menu ditambahkan.");
            } else {
                System.out.println("-> Dibatalkan.");
            }
        }
    }

    static void adminUbahHarga() {
        int[] map = tampilDaftarMenuBernomor();
        System.out.println(map[0]); // bernomor tunggal untuk pilih
        if (map[0] == -1) {
            System.out.println("Tidak ada menu");
            return;
        }
        int no = inputInt("Masukkan nomor menu yang diubah harganya: ");
        int idx = nomorKeIndex(no, map);
        if (idx == -1) { System.out.println("Nomor tidak valid."); return; }

        System.out.println("Target: " + menu[idx].nama + " (sekarang " + rupiah.format(menu[idx].harga) + ")");
        int hargaBaru = inputInt("Harga baru: ");
        String k = inputString("Yakin ubah? (Ya/Tidak): ").trim();
        if (k.equalsIgnoreCase("Ya")) {
            menu[idx].harga = hargaBaru;
            System.out.println("-> Harga diubah.");
        } else {
            System.out.println("-> Dibatalkan.");
        }
    }

    static void adminHapusMenu() {
        int[] map = tampilDaftarMenuBernomor();
        if (map[0] == -1) {
            System.out.println("Tidak ada menu.");
            return;
        }
        int no = inputInt("Masukkan nomor menu yang dihapus: ");
        int idx = nomorKeIndex(no, map);
        if (idx == -1) { System.out.println("Nomor tidak valid."); return; }

        System.out.println("Target: " + menu[idx].nama + " (" + menu[idx].kategori + ")");
        String k = inputString("Yakin hapus? (Ya/Tidak): ").trim();
        if (k.equalsIgnoreCase("Ya")) {
            menu[idx] = null;
            System.out.println("-> Menu dihapus.");
        } else {
            System.out.println("-> Dibatalkan.");
        }
    }

    // Tampilkan semua item non-null bernomor (untuk pilih no)
    static int[] tampilDaftarMenuBernomor() {
        System.out.println("\n-- DAFTAR MENU (BERNOMOR) --");
        int[] map = new int[MAX_MENU + 1]; // map nomor->index menu[]
        for (int i = 0; i < map.length; i++) map[i] = -1;

        int nomor = 1;
        for (int i = 0; i < menu.length; i++) {
            if (menu[i] != null) {
                map[nomor] = i;
                System.out.printf("%2d. %-10s %-20s %s%n", nomor, menu[i].kategori, menu[i].nama, rupiah.format(menu[i].harga));
                nomor++;
            }
        }
        if (nomor == 1) {
            System.out.println("(kosong)");
            map[0] = -1; // tanda kosong
        } else {
            map[0] = nomor - 1; // simpan jumlah item yang ditampilkan di map[0]
        }
        return map;
    }

    static int nomorKeIndex(int no, int[] map) {
        if (no <= 0 || no >= map.length) return -1;
        return map[no];
    }

    static int cariSlotKosongMenu() {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i] == null) return i;
        }
        return -1;
    }

    // ====== Helpers Input ======
    static int inputInt(String label) {
        while (true) {
            try {
                System.out.print(label);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Harus angka. Coba lagi.");
            }
        }
    }

    static String inputString(String label) {
        System.out.print(label);
        return sc.nextLine();
    }

    static String inputKategori(String label) {
        while (true) {
            String k = inputString(label).trim().toUpperCase();
            if (k.equals("MAKANAN") || k.equals("MINUMAN")) return k;
            System.out.println("Kategori harus 'MAKANAN' atau 'MINUMAN'.");
        }
    }
}
