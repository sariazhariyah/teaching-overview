<?php
class SI2B
{ //body class
    var $namaMahasiswa;

    public function belajar()
    {
        //daftar list mata kuliah
    }
}

// class Kendaraan
// {
//     var $jumlahRoda;
//     var $warna;
//     var $bahanBakar;
//     var $harga;
//     var $merek;

//     public function statusHarga(): int
//     {
//         //Untuk menentukan harga kendaraan apakah mahal atau murah
//         // Misal: harga >= 100000000 dianggap mahal (return 1), selain itu murah (return 0)
//         if ($this->harga >= 100000000) {
//             return 1; // mahal
//         } else {
//             return 0; // murah
//         }
//     }
// }

class Kendaraan
{
    public $jumlahRoda = 4;
    public $warna;
    public $bahanBakar = "Premium";
    public $harga = 100000000;
    public $merek;
    public $tahunPembuatan = 2004;

    public function statusHarga()
    {
        if ($this->harga > 50000000) {
            $status = "Harga Kendaraan Mahal";
        } else {
            $status = "Harga Kendaraan Murah";
        }
        return $status;
    }

    public function statusSubsidi()
    {
        if ($this->tahunPembuatan < 2005 && $this->bahanBakar == "Premium") {
            $status = "DAPAT SUBSIDI";
        } else {
            $status = "TIDAK DAPAT SUBSIDI";
        }
        return $status;
    }
}
//instansiasi kelas
$ObjekKendaraan = new Kendaraan(); //pembuatan objek dari kelas
echo "jumlahRoda : " . $ObjekKendaraan->$jumlahRoda . "<br />"; //proses instansiasi pemanggilan variable
echo "Status Harga : " . $ObjekKendaraan->statusHarga() . "<br />"; //proses instansiasi/pemanggilan function dari kelas
echo "Status Subsidi : " . $ObjekKendaraan->statusSubsidi();

//Objek 1
//deklarasi objek dan instansiasi objek (berada di luar class)
$objekKendaraan1 = new Kendaraan;
//setting properties
$objekKendaraan1->harga = 1000000;
$objekKendaraan1->tahunPembuatan = 1999;
//instansiasi objek (pemanggilan method/function)
echo "Status Harga : " . $objekKendaraan->statusHarga();


//Objek 2
//deklarasi objek dan instansiasi objek (berada di luar class)
$objekKendaraan2 = new Kendaraan;
//setting properties  
$objekKendaraan2->bahanBakar = "Pertamax";
$objekKendaraan2->tahunPembuatan = 1999;
//instansiasi objek (pemanggilan method/function)
echo "<br>";
echo "Status BBM: " . $objekKendaraan2->statusSubsidi();
echo "<br>";
//echo "Harga Bekas: " . $objekKendaraan2->hargaSecondKendaraan();
