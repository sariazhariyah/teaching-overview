import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Pesanan {
    private final List<OrderLine> lines = new ArrayList<>();

    public void tambah(MenuItem item, int qty) {
        // Jika item sama sudah ada, tambahkan qty-nya.
        for (OrderLine ol : lines) {
            if (ol.getItem().getNama().equalsIgnoreCase(item.getNama())) {
                ol.setQty(ol.getQty() + qty);
                return;
            }
        }
        lines.add(new OrderLine(item, qty));
    }

    public List<OrderLine> getLines() { return Collections.unmodifiableList(lines); }

    public double hitungSubtotal() {
        double sum = 0;
        for (OrderLine ol : lines) sum += ol.getSubTotal();
        return sum;
    }

    /** Terapkan diskon berdasarkan target kategori.
     *  - Diskon "ALL" diterapkan pada total semua item.
     *  - Diskon kategori spesifik (Makanan/Minuman) hanya pada item kategori itu.
     *  Jika ada banyak diskon, semuanya diterapkan berurutan (komposisi).
     */
    public double hitungTotalDenganDiskon(List<Diskon> diskonList) {
        double total = 0;
        double subtotalMakanan = 0;
        double subtotalMinuman = 0;

        for (OrderLine ol : lines) {
            String kat = ol.getItem().getKategori();
            if ("Makanan".equalsIgnoreCase(kat)) subtotalMakanan += ol.getSubTotal();
            else if ("Minuman".equalsIgnoreCase(kat)) subtotalMinuman += ol.getSubTotal();
            total += ol.getSubTotal();
        }

        double totalSetelahDiskon = total;
        for (Diskon d : diskonList) {
            double p = d.getPersenDiskon() / 100.0;
            String target = d.getTargetKategori();
            if ("ALL".equalsIgnoreCase(target)) {
                totalSetelahDiskon -= totalSetelahDiskon * p;
            } else if ("Makanan".equalsIgnoreCase(target)) {
                totalSetelahDiskon -= subtotalMakanan * p;
            } else if ("Minuman".equalsIgnoreCase(target)) {
                totalSetelahDiskon -= subtotalMinuman * p;
            }
        }
        return totalSetelahDiskon;
    }

    public String formatRupiah(double v) {
        return NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(v);
    }

    /** Simpan struk ke file teks di folder struk/ */
    public Path simpanStrukKeFile(List<Diskon> diskonTerpakai, double totalAkhir) throws IOException {
        Files.createDirectories(Paths.get("struk"));
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path out = Paths.get("struk", "STRUK-" + ts + ".txt");

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out))) {
            pw.println("===== STRUK PESANAN =====");
            pw.println("Waktu : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pw.println();
            pw.println(String.format("%-25s %5s %12s %12s", "Item", "Qty", "Harga", "SubTotal"));
            pw.println("-------------------------------------------------------------");
            for (OrderLine ol : lines) {
                pw.println(String.format("%-25s %5d %12s %12s",
                        ol.getItem().getNama(),
                        ol.getQty(),
                        formatRupiah(ol.getItem().getHarga()),
                        formatRupiah(ol.getSubTotal())));
            }
            pw.println("-------------------------------------------------------------");
            pw.println("Subtotal        : " + formatRupiah(hitungSubtotal()));
            if (!diskonTerpakai.isEmpty()) {
                pw.println("Diskon diterapkan:");
                for (Diskon d : diskonTerpakai) {
                    pw.println(String.format(" - %s (%s%% untuk %s)",
                            d.getNama(), d.getPersenDiskon(), d.getTargetKategori()));
                }
            } else {
                pw.println("Diskon diterapkan: -");
            }
            pw.println("TOTAL AKHIR     : " + formatRupiah(totalAkhir));
            pw.println("=========================");
        }
        return out;
    }
}
