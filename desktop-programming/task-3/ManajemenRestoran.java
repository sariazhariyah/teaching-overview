import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

public class ManajemenRestoran {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Menu menu = new Menu();
    private static Pesanan pesanan = new Pesanan();

    public static void main(String[] args) {
        // Coba muat menu dari file di awal (Operasi File)
        try {
            menu.muatMenuDariFile("menu.txt");
        } catch (IOException e) {
            System.out.println("Gagal memuat menu dari file: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU UTAMA MANAJEMEN RESTORAN ===");
            System.out.println("1. Tampilkan Menu Restoran");
            System.out.println("2. Tambah Item Menu Baru");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Tampilkan Struk Pesanan (Objek)");
            System.out.println("5. Simpan Menu & Struk ke File");
            System.out.println("6. Tampilkan Struk Pesanan dari File");
            System.out.println("7. Keluar");
            System.out.println("=====================================");
            int pilihan = inputInt("Pilih menu (1-7): ");

            try {
                switch (pilihan) {
                    case 1:
                        menu.tampilMenu();
                        break;
                    case 2:
                        tambahItemMenu();
                        break;
                    case 3:
                        buatPesanan();
                        break;
                    case 4:
                        pesanan.tampilStruk();
                        break;
                    case 5:
                        simpanData();
                        break;
                    case 6:
                        tampilStrukDariFile();
                        break;
                    case 7:
                        running = false;
                        System.out.println("Terima kasih. Program selesai.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (ItemNotFoundException e) {
                // Contoh penggunaan Exception sesuai rubrik
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Kesalahan I/O: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan lain: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void tambahItemMenu() {
        System.out.println("\n-- Tambah Item Menu --");
        System.out.println("a. Makanan");
        System.out.println("b. Minuman");
        System.out.println("c. Diskon");
        System.out.print("Pilih jenis item (a/b/c): ");
        String jenis = scanner.nextLine().trim();

        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine().trim();

        if (jenis.equalsIgnoreCase("a")) {
            System.out.print("Jenis makanan: ");
            String jenisMakanan = scanner.nextLine().trim();
            double harga = inputDouble("Harga: ");
            menu.tambahMenu(new Makanan(nama, harga, jenisMakanan));
        } else if (jenis.equalsIgnoreCase("b")) {
            System.out.print("Jenis minuman: ");
            String jenisMinuman = scanner.nextLine().trim();
            double harga = inputDouble("Harga: ");
            menu.tambahMenu(new Minuman(nama, harga, jenisMinuman));
        } else if (jenis.equalsIgnoreCase("c")) {
            double diskon = inputDouble("Besar diskon (%): ");
            menu.tambahMenu(new Diskon(nama, diskon));
        } else {
            System.out.println("Jenis tidak dikenal.");
        }
    }

    private static void buatPesanan() throws ItemNotFoundException {
        pesanan = new Pesanan(); // reset pesanan baru
        boolean lanjut = true;

        while (lanjut) {
            menu.tampilMenu();
            int noMenu = inputInt("Pilih nomor menu yang akan dipesan (0 untuk selesai): ");

            if (noMenu == 0) {
                lanjut = false;
            } else {
                // index di list = noMenu - 1
                MenuItem itemDipilih = menu.getMenuItem(noMenu - 1); // bisa lempar ItemNotFoundException
                pesanan.tambahPesanan(itemDipilih);
                System.out.println(itemDipilih.getNama() + " ditambahkan ke pesanan.");
            }
        }

        pesanan.hitungTotal();
        System.out.println("Pesanan selesai dibuat.");
    }

    private static void simpanData() throws IOException {
        menu.simpanMenuKeFile("menu.txt");
        pesanan.simpanStrukKeFile("struk.txt");
        System.out.println("Menu disimpan ke menu.txt dan struk disimpan ke struk.txt");
    }

    private static void tampilStrukDariFile() throws IOException {
        pesanan.muatStrukDariFile("struk.txt");
    }

    // ====== Helper input dengan penanganan InputMismatchException ======
    private static int inputInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa bilangan bulat. Coba lagi.");
            }
        }
    }

    private static double inputDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka (misal 12000 atau 12000.5). Coba lagi.");
            }
        }
    }
}

