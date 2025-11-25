/** Satu baris pesanan: item + kuantitas */
public class OrderLine {
    private MenuItem item;
    private int qty;

    public OrderLine(MenuItem item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public MenuItem getItem() { return item; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getSubTotal() {
        return item.getHarga() * qty;
    }
}
