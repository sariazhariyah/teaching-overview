import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Util kecil untuk menampilkan tabel teks rapi di CLI */
public class TextTable {
    private final String[] headers;
    private final List<String[]> rows = new ArrayList<>();

    public TextTable(String[] headers) {
        this.headers = headers;
    }

    public void addRow(String[] row) {
        rows.add(row);
    }

    public void print() {
        int[] widths = Arrays.stream(headers).mapToInt(String::length).toArray();
        for (String[] r : rows) {
            for (int i = 0; i < r.length; i++) {
                widths[i] = Math.max(widths[i], r[i] == null ? 0 : r[i].length());
            }
        }
        // header
        for (int i = 0; i < headers.length; i++) {
            System.out.print(pad(headers[i], widths[i]));
            if (i < headers.length - 1) System.out.print(" | ");
        }
        System.out.println();
        // separator
        int total = 0;
        for (int w : widths) total += w;
        System.out.println("-".repeat(total + 3 * (headers.length - 1)));
        // rows
        for (String[] r : rows) {
            for (int i = 0; i < r.length; i++) {
                System.out.print(pad(r[i], widths[i]));
                if (i < r.length - 1) System.out.print(" | ");
            }
            System.out.println();
        }
    }

    private String pad(String s, int w) {
        if (s == null) s = "";
        if (s.length() >= w) return s;
        return s + " ".repeat(w - s.length());
    }
}
