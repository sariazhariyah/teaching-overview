new Vue({

el:'#stokApp',

data:{

upbjjList: app.$data.upbjjList,
kategoriList: app.$data.kategoriList,
stok: app.$data.stok,

filterUpbjj:'',
filterKategori:'',
sortBy:'',
warningOnly:false,

errorMessage:'',

form:{
kode:'',
judul:'',
kategori:'',
upbjj:'',
lokasiRak:'',
harga:0,
qty:0,
safety:0,
catatanHTML:''
}

},

computed:{

filteredStok(){

let data = [...this.stok];

if(this.filterUpbjj){

data = data.filter(
d => d.upbjj == this.filterUpbjj
);

}

if(this.filterKategori){

data = data.filter(
d => d.kategori == this.filterKategori
);

}

if(this.warningOnly){

data = data.filter(
d => d.qty < d.safety || d.qty == 0
);

}

if(this.sortBy == 'judul'){

data.sort((a,b)=>
a.judul.localeCompare(b.judul)
);

}

if(this.sortBy == 'qty'){

data.sort((a,b)=>
a.qty - b.qty
);

}

if(this.sortBy == 'harga'){

data.sort((a,b)=>
a.harga - b.harga
);

}

return data;

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

resetFilter(){

this.filterUpbjj='';
this.filterKategori='';
this.sortBy='';
this.warningOnly=false;

},

tambahData(){

if(
!this.form.kode ||
!this.form.judul ||
!this.form.kategori ||
!this.form.upbjj
){

this.errorMessage =
'Semua field wajib diisi';

return;

}

this.errorMessage='';

this.stok.push({
...this.form
});

this.form = {
kode:'',
judul:'',
kategori:'',
upbjj:'',
lokasiRak:'',
harga:0,
qty:0,
safety:0,
catatanHTML:''
};

alert('Data berhasil ditambahkan');

}

},

watch:{

filterUpbjj(newValue){

console.log(
'UPBJJ berubah:',
newValue
);

this.filterKategori='';

},

warningOnly(newValue){

console.log(
'Warning filter:',
newValue
);

}

}

});