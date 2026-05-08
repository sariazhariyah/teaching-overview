
document.addEventListener('DOMContentLoaded', function() {
    // Load data
    if (typeof dataPengguna === 'undefined') {
        console.error('Data tidak ditemukan. Pastikan file data.js sudah di-load.');
        return;
    }

    // Greeting berdasarkan waktu
    function getGreeting() {
        const hour = new Date().getHours();
        if (hour < 12) return 'Selamat Pagi';
        if (hour < 17) return 'Selamat Siang';
        return 'Selamat Sore';
    }

    // Login validation
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            validateLogin();
        });
    }

    function validateLogin() {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Validasi format email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            showAlert('Format email tidak valid!', 'error');
            return;
        }

        if (!email || !password) {
            showAlert('Email dan password harus diisi!', 'error');
            return;
        }

        // Cek kredensial
        const user = dataPengguna.find(u => 
            u.email.toLowerCase() === email.toLowerCase() && u.password === password
        );

        if (user) {
            showAlert(`Login berhasil! Selamat datang, ${user.nama}`, 'success');
            localStorage.setItem('loggedInUser', JSON.stringify(user));
            setTimeout(() => {
                window.location.href = 'dashboard.html';
            }, 1500);
        } else {
            showAlert('Email/password yang anda masukkan salah!', 'error');
        }
    }

    // Modal handlers
    const modalTriggers = document.querySelectorAll('[data-modal]');
    modalTriggers.forEach(trigger => {
        trigger.addEventListener('click', function() {
            const modalId = this.getAttribute('data-modal');
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.style.display = 'block';
            }
        });
    });

    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        const closeBtn = modal.querySelector('.close');
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                modal.style.display = 'none';
            }
        });
        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                modal.style.display = 'none';
            });
        }
    });

    // Dashboard greeting
    const greetingEl = document.getElementById('greeting');
    if (greetingEl) {
        greetingEl.textContent = getGreeting();
    }

    // Tracking
    const trackingForm = document.getElementById('trackingForm');
    if (trackingForm) {
        trackingForm.addEventListener('submit', function(e) {
            e.preventDefault();
            searchTracking();
        });
    }

    function searchTracking() {
        const nomorDO = document.getElementById('nomorDO').value;
        const resultEl = document.getElementById('trackingResult');
        const progressFill = document.getElementById('progressFill');

        if (!nomorDO) {
            showAlert('Nomor Delivery Order harus diisi!', 'error');
            return;
        }

        const trackingData = dataTracking[nomorDO];
        if (trackingData) {
            // Tampilkan data tracking
            document.getElementById('trackingNama').textContent = trackingData.nama;
            document.getElementById('trackingStatus').textContent = trackingData.status;
            document.getElementById('trackingStatus').className = `status-badge status-${trackingData.status.toLowerCase().replace(' ', '-')}`;
            document.getElementById('trackingEkspedisi').textContent = trackingData.ekspedisi;
            document.getElementById('trackingTanggal').textContent = trackingData.tanggalKirim;
            document.getElementById('trackingPaket').textContent = trackingData.paket;
            document.getElementById('trackingTotal').textContent = trackingData.total;

            // Progress bar
            let progress = 30;
            if (trackingData.status === 'Dalam Perjalanan') progress = 60;
            else if (trackingData.status === 'Diterima') progress = 100;
            progressFill.style.width = progress + '%';

            // Riwayat perjalanan
            const riwayatEl = document.getElementById('trackingRiwayat');
            riwayatEl.innerHTML = trackingData.perjalanan.map(item => 
                `<li><strong>${item.waktu}</strong>: ${item.keterangan}</li>`
            ).join('');

            resultEl.style.display = 'block';
            resultEl.scrollIntoView({ behavior: 'smooth' });
        } else {
            showAlert('Nomor Delivery Order tidak ditemukan!', 'error');
        }
    }

    // Stok management
    function loadStokData() {
        const stokContainer = document.getElementById('stokTableBody');
        if (!stokContainer) return;

        stokContainer.innerHTML = dataBahanAjar.map((item, index) => `
            <tr>
                <td>${item.kodeLokasi}</td>
                <td>${item.kodeBarang}</td>
                <td>${item.namaBarang}</td>
                <td>${item.jenisBarang}</td>
                <td>Edisi ${item.edisi}</td>
                <td class="${getStokClass(item.stok)}">${item.stok}</td>
                <td><img src="${item.cover}" alt="${item.namaBarang}" style="width: 50px; height: 70px; object-fit: cover; border-radius: 5px;"></td>
            </tr>
        `).join('');
    }

    function getStokClass(stok) {
        if (stok < 100) return 'stok-low';
        if (stok < 300) return 'stok-medium';
        return 'stok-high';
    }

    // Add stok form
    const addStokForm = document.getElementById('addStokForm');
    if (addStokForm) {
        addStokForm.addEventListener('submit', function(e) {
            e.preventDefault();
            addStokItem();
        });
    }

    function addStokItem() {
        const kodeLokasi = document.getElementById('newKodeLokasi').value;
        const kodeBarang = document.getElementById('newKodeBarang').value;
        const namaBarang = document.getElementById('newNamaBarang').value;
        const jenisBarang = document.getElementById('newJenisBarang').value;
        const edisi = document.getElementById('newEdisi').value;
        const stok = parseInt(document.getElementById('newStok').value);

        if (!kodeLokasi || !kodeBarang || !namaBarang || !jenisBarang || !edisi || !stok) {
            showAlert('Semua field harus diisi!', 'error');
            return;
        }

        // Tambah ke data
        dataBahanAjar.push({
            kodeLokasi,
            kodeBarang,
            namaBarang,
            jenisBarang,
            edisi,
            stok,
            cover: 'img/placeholder.jpg'
        });

        // Refresh table
        loadStokData();

        // Reset form
        addStokForm.reset();
        showAlert('Stok baru berhasil ditambahkan!', 'success');
    }

    // Load stok data on page load
    if (document.getElementById('stokTableBody')) {
        loadStokData();
    }

    // Check login status
    function checkLoginStatus() {
        const user = localStorage.getItem('loggedInUser');
        if (!user && window.location.pathname.includes('dashboard')) {
            window.location.href = 'index.html';
        }
    }
    checkLoginStatus();

    // Alert function
    function showAlert(message, type = 'info') {
        // Remove existing alerts
        const existingAlert = document.querySelector('.custom-alert');
        if (existingAlert) {
            existingAlert.remove();
        }

        const alertDiv = document.createElement('div');
        alertDiv.className = `custom-alert alert-${type}`;
        alertDiv.style.cssText = `
            position: fixed;
            top: 100px;
            right: 20px;
            padding: 1rem 2rem;
            border-radius: 10px;
            color: white;
            font-weight: 600;
            z-index: 3000;
            transform: translateX(400px);
            transition: all 0.3s ease;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        `;

        if (type === 'success') alertDiv.style.background = '#27ae60';
        else if (type === 'error') alertDiv.style.background = '#e74c3c';
        else alertDiv.style.background = '#3498db';

        alertDiv.textContent = message;
        document.body.appendChild(alertDiv);

        // Animate in
        setTimeout(() => {
            alertDiv.style.transform = 'translateX(0)';
        }, 100);

        // Auto remove
        setTimeout(() => {
            alertDiv.style.transform = 'translateX(400px)';
            setTimeout(() => {
                if (alertDiv.parentNode) {
                    alertDiv.parentNode.removeChild(alertDiv);
                }
            }, 300);
        }, 4000);
    }
});