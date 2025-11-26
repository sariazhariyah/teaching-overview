// Subkelas Diskon (Inheritance dari MenuItem)
public class Diskon extends MenuItem {
    private double persentaseDiskon; // contoh: 10 berarti 10%

    public Diskon(String nama, double persentaseDiskon) {
        // Harga 0 karena ini hanya informasi diskon
        super(nama, 0, "Diskon");
        this.persentaseDiskon = persentaseDiskon;
    }

    // Getter & Setter tambahan (Encapsulation)
    public double getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public void setPersentaseDiskon(double persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }

    // Polymorphism: override tampilMenu()
    @Override
    public void tampilMenu() {
        System.out.println("[Diskon] " + getNama()
                + " | Besar Diskon: " + persentaseDiskon + "%");
    }
}
