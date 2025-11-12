class Mahasiswa {
    String nama;
    int nim;


    void tampil() {
        System.out.println("Nama: " + nama + ", NIM: " + nim);
    }
}


public class Main {
    public static void main(String[] args) {
        Mahasiswa m = new Mahasiswa();
        m.nama = "Rani";
        m.nim = 123456;
        m.tampil();
    }
}
