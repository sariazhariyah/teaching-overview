new Vue({

el:'#trackingApp',

data:{

paket: app.$data.paket,
pengirimanList: app.$data.pengirimanList,
tracking: app.$data.tracking,

selectedPaket:'',

form:{
nim:'',
nama:'',
ekspedisi:'',
tanggalKirim:
new Date()
.toISOString()
.substr(0,10)
}

},

computed:{

nomorDOBaru(){

let jumlah =
Object.keys(this.tracking).length + 1;

let tahun =
new Date().getFullYear();

return `DO${tahun}-${String(jumlah).padStart(4,'0')}`;

}

},

methods:{

formatRupiah(angka){

return new Intl.NumberFormat(
'id-ID',
{
style:'currency',
currency:'IDR'
}
).format(angka);

},

tambahDO(){

if(
!this.form.nim ||
!this.form.nama ||
!this.form.ekspedisi ||
!this.selectedPaket
){

alert('Lengkapi data');

return;

}

this.tracking[this.nomorDOBaru] = {

nim:this.form.nim,
nama:this.form.nama,
status:'Diproses',
ekspedisi:this.form.ekspedisi,
tanggalKirim:this.form.tanggalKirim,
paket:this.selectedPaket.kode,
total:this.selectedPaket.harga,

perjalanan:[
{
waktu:new Date().toLocaleString(),
keterangan:'DO berhasil dibuat'
}
]

};

alert('DO berhasil dibuat');

this.form = {
nim:'',
nama:'',
ekspedisi:'',
tanggalKirim:
new Date()
.toISOString()
.substr(0,10)
};

this.selectedPaket='';

}

},

watch:{

selectedPaket(newValue){

console.log(
'Paket dipilih:',
newValue
);

},

'form.ekspedisi'(newValue){

console.log(
'Ekspedisi berubah:',
newValue
);

}

}

});