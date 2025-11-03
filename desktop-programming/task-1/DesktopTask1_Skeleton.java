// ======================================================
// Template Panduan / Skeleton Code Tugas 1
// ======================================================
// 📌 Petunjuk:
// - Kode ini contoh struktur dasar (bisa dijalankan langsung di VS Code).
// - Silakan pelajari alur dan lengkapi bagian bertanda TODO.
// - Jangan hanya salin tempel, tapi pahami setiap langkahnya.
// ======================================================

import java.util.Scanner;

class Menu {
    // ====== Atribut (encapsulation sederhana) ======
    private String nama;
    private int harga;
    private String kategori; // "MAKANAN" atau "MINUMAN"

    // ====== Konstruktor ======
    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // ====== Getter ======
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public String getKategori() { return kategori; }

    public boolean isMinuman() {
        return kategori.equalsIgnoreCase("MINUMAN");
    }
}

public class DesktopTask1_Skeleton {
    // ====== ARRAY MENU (DATA STATIS) ======
    static Menu[] makanan = {
        new Menu("Nasi Padang", 25000, "MAKANAN"),
        new Menu("Ayam Bakar", 30000, "MAKANAN"),
        new Menu("Rendang", 35000, "MAKANAN"),
        new Menu("Sate Ayam", 28000, "MAKANAN")
    };

    static Menu[] minuman = {
        new Menu("Es Teh", 8000, "MINUMAN"),
        new Menu("Es Jeruk", 10000, "MINUMAN"),
        new Menu("Kopi", 12000, "MINUMAN"),
        new Menu("Jus Alpukat", 18000, "MINUMAN")
    };

    // ====== Struktur Pesanan ======
    static class OrderLine {
        Menu menu;
        int qty;
        int bayarQty;
        int totalBaris;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        tampilkanMenu();

        System.out.println("\nFormat input: Nama Menu = jumlah (maks 4 pesanan)");
        System.out.println("Kosongkan baris jika tidak digunakan.\n");

        // ====== TODO 1: Ambil input 4 pesanan tanpa loop ======
        OrderLine p1 = ambilPesanan(sc, "Pesanan 1");
        OrderLine p2 = ambilPesanan(sc, "Pesanan 2");
        OrderLine p3 = ambilPesanan(sc, "Pesanan 3");
        OrderLine p4 = ambilPesanan(sc, "Pesanan 4");

        // ====== TODO 2: Hitung total awal sebelum promo ======
        int totalAwal = subtotal(p1) + subtotal(p2) + subtotal(p3) + subtotal(p4);

        // ====== Promo Beli 1 Gratis 1 jika total > 50.000 ======
        boolean eligibleBOGO = totalAwal > 50000;
        if (eligibleBOGO) applyBOGO(p1);
        if (eligibleBOGO) applyBOGO(p2);
        if (eligibleBOGO) applyBOGO(p3);
        if (eligibleBOGO) applyBOGO(p4);

        // ====== Hitung total setelah promo ======
        int subtotalBOGO = total(p1) + total(p2) + total(p3) + total(p4);

        // ====== Diskon 10% jika subtotal > 100.000 ======
        int diskon10 = (subtotalBOGO > 100000) ? (int)(subtotalBOGO * 0.10) : 0;
        int setelahDiskon = subtotalBOGO - diskon10;

        // ====== Pajak dan Layanan ======
        int pajak10 = (int)(setelahDiskon * 0.10);
        int layanan = 20000;

        int grandTotal = setelahDiskon + pajak10 + layanan;

        // ====== TODO 3: Cetak struk (panggil method cetakStruk) ======
        cetakStruk(p1, p2, p3, p4, totalAwal, eligibleBOGO, subtotalBOGO, diskon10, pajak10, layanan, grandTotal);

        sc.close();
    }

    // ====== Menampilkan daftar menu ======
    static void tampilkanMenu() {
        System.out.println("=== DAFTAR MENU RESTORAN ===");
        System.out.println("Kategori: MAKANAN");
        for (Menu m : makanan) System.out.printf("- %s (Rp %,d)%n", m.getNama(), m.getHarga());

        System.out.println("\nKategori: MINUMAN");
        for (Menu m : minuman) System.out.printf("- %s (Rp %,d)%n", m.getNama(), m.getHarga());
    }

    // ====== Input Pesanan ======
    static OrderLine ambilPesanan(Scanner sc, String label) {
        System.out.print(label + " (contoh: Nasi Padang = 2): ");
        String line = sc.nextLine().trim();

        if (line.isEmpty()) return null;

        String[] parts = line.split("=");
        if (parts.length != 2) {
            System.out.println("❌ Format salah, gunakan: Nama Menu = jumlah");
            return null;
        }

        String nama = parts[0].trim();
        int jumlah = Integer.parseInt(parts[1].trim());
        Menu menu = cariMenu(nama);

        if (menu == null) {
            System.out.println("❌ Menu tidak ditemukan: " + nama);
            return null;
        }

        OrderLine ol = new OrderLine();
        ol.menu = menu;
        ol.qty = jumlah;
        ol.bayarQty = jumlah;
        ol.totalBaris = menu.getHarga() * jumlah;
        return ol;
    }

    // ====== Cari menu berdasarkan nama ======
    static Menu cariMenu(String nama) {
        for (Menu m : makanan) if (m.getNama().equalsIgnoreCase(nama)) return m;
        for (Menu m : minuman) if (m.getNama().equalsIgnoreCase(nama)) return m;
        return null;
    }

    // ====== Hitung subtotal dan total per baris ======
    static int subtotal(OrderLine ol) {
        if (ol == null) return 0;
        return ol.menu.getHarga() * ol.qty;
    }

    static int total(OrderLine ol) {
        if (ol == null) return 0;
        return ol.totalBaris;
    }

    // ====== Promo BOGO (Beli 1 Gratis 1) ======
    static void applyBOGO(OrderLine ol) {
        if (ol == null) return;
        if (ol.menu.isMinuman()) {
            int gratis = ol.qty / 2;
            ol.bayarQty = ol.qty - gratis;
            ol.totalBaris = ol.menu.getHarga() * ol.bayarQty;
        }
    }

    // ====== Cetak Struk Pembayaran ======
    static void cetakStruk(OrderLine p1, OrderLine p2, OrderLine p3, OrderLine p4,
                           int totalAwal, boolean eligBOGO, int subtotalBOGO,
                           int diskon10, int pajak10, int layanan, int grandTotal) {

        System.out.println("\n========== STRUK PEMBAYARAN ==========");
        tampilkanBaris(p1, eligBOGO);
        tampilkanBaris(p2, eligBOGO);
        tampilkanBaris(p3, eligBOGO);
        tampilkanBaris(p4, eligBOGO);

        System.out.printf("\nTotal awal: Rp %,d%n", totalAwal);
        if (eligBOGO)
            System.out.println("Promo BOGO aktif (minuman beli 1 gratis 1)");
        System.out.printf("Subtotal setelah BOGO: Rp %,d%n", subtotalBOGO);
        if (diskon10 > 0)
            System.out.printf("Diskon 10%%: -Rp %,d%n", diskon10);
        System.out.printf("Pajak (10%%): Rp %,d%n", pajak10);
        System.out.printf("Biaya Layanan: Rp %,d%n", layanan);
        System.out.println("--------------------------------------");
        System.out.printf("TOTAL BAYAR: Rp %,d%n", grandTotal);
        System.out.println("======================================");
        System.out.println("Terima kasih telah berkunjung!");
    }

    static void tampilkanBaris(OrderLine ol, boolean eligBOGO) {
        if (ol == null) return;
        String info = (eligBOGO && ol.menu.isMinuman() && ol.qty >= 2)
                ? " (BOGO aktif)" : "";
        System.out.printf("%-20s x%-3d Rp %,d%s%n", ol.menu.getNama(), ol.qty, ol.totalBaris, info);
    }
}
