import java.util.Arrays;

public class StringArrayMethod {
    public static void main(String[] args) {
        String kalimat = "Belajar Java Itu Seru";
        System.out.println(kalimat.toUpperCase());


        String[] kata = kalimat.split(" ");
        System.out.println(Arrays.toString(kata));
    }
}
