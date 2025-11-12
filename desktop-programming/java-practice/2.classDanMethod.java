class Mahasiswa {
    String nama;
    int nim;


    void tampil() {
        System.out.println("Nama: " + nama + ", NIM: " + nim);
    }
}


public class DemoMahasiswa {
    public static void main(String[] args) {
        Mahasiswa mhs = new Mahasiswa();
        mhs.nama = "Andi";
        mhs.nim = 12345;
        mhs.tampil();
    }
}