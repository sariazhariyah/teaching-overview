<?php
class Pegadaian
{
    var $namaToko = "TOKO PEGADAIAN SYARIAH";
    var $alamat = "JI Keadilan No 16";
    var $telepon = "Telp. 72353459";

    var $pinjamanPokok;
    var $bungaPersen;
    var $lamaAngsuran; // dalam bulan
    var $keterlambatan; // dalam hari

    // hitung total pinjaman (pokok + bunga)
    function totalPinjaman()
    {
        $bunga = $this->pinjamanPokok * ($this->bungaPersen / 100);
        $total = $this->pinjamanPokok + $bunga;
        return $total;
    }

    // hitung angsuran per bulan
    function angsuranPerBulan()
    {
        return $this->totalPinjaman() / $this->lamaAngsuran;
    }

    // hitung denda keterlambatan
    function dendaKeterlambatan()
    {
        $angsuran = $this->angsuranPerBulan();
        $denda = $angsuran * 0.0015 * $this->keterlambatan; // 0.15% = 0.0015
        return $denda;
    }

    // hitung total pembayaran (angsuran + denda)
    function totalPembayaran()
    {
        return $this->angsuranPerBulan() + $this->dendaKeterlambatan();
    }
}

// Membuat objek pegadaian
$hitung = new Pegadaian();

// Set data
$hitung->pinjamanPokok = 1000000; // Rp 1.000.000
$hitung->bungaPersen = 10;        // 10%
$hitung->lamaAngsuran = 5;        // 5 bulan
$hitung->keterlambatan = 40;      // 40 hari

// Output
echo "<b>" . $hitung->namaToko . "</b><br>";
echo $hitung->alamat . "<br>";
echo $hitung->telepon . "<br><br>";

echo "Program Penghitung Besaran Angsuran Hutang<br>";
echo "Besaran Pinjaman : Rp. " . number_format($hitung->pinjamanPokok, 0, ',', '.') . "<br>";
echo "Masukan besar bunga (%): " . $hitung->bungaPersen . "<br>";
echo "Total Pinjaman : Rp. " . number_format($hitung->totalPinjaman(), 0, ',', '.') . "<br>";
echo "Lama angsuran (bulan) : " . $hitung->lamaAngsuran . "<br>";
echo "Besaran angsuran : Rp. " . number_format($hitung->angsuranPerBulan(), 0, ',', '.') . "<br><br>";

echo "Keterlambatan Angsuran (Hari): " . $hitung->keterlambatan . "<br>";
echo "Denda Keterlambatan : Rp. " . number_format($hitung->dendaKeterlambatan(), 0, ',', '.') . "<br>";
echo "Besaran Pembayaran : Rp. " . number_format($hitung->totalPembayaran(), 0, ',', '.');
