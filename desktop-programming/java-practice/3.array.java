public class ContohArray {
    public static void main(String[] args) {
        String[] menu = {"Nasi Goreng", "Mie Ayam", "Sate"};
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i+1) + ". " + menu[i]);
        }
    }
}
