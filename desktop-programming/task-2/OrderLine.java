public class OrderLine {
    Menu item;
    int qty;
    int gratisBogo; // unit gratis dari promo BOGO MINUMAN

    public OrderLine(Menu item, int qty) {
        this.item = item;
        this.qty = qty;
        this.gratisBogo = 0;
    }

    public int totalSebelumBogo() {
        return item.harga * qty;
    }

    public int totalSetelahBogo() {
        int qtyBayar = qty - gratisBogo;
        if (qtyBayar < 0) qtyBayar = 0;
        return item.harga * qtyBayar;
    }
}
