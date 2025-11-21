
# SmartAlert: Aplikasi Keamanan Pribadi dengan Analisis Konteks Visual Berbasis Cloud AI

**SmartAlert** adalah aplikasi keamanan pribadi berbasis Android yang dirancang untuk memberikan respons darurat yang cepat dan kaya akan konteks. Saat diaktifkan, aplikasi secara otomatis mengumpulkan data lokasi (GPS) dan bukti visual (Kamera), lalu mengirimkannya ke layanan Cloud AI untuk analisis. Hasilnya dirangkum menjadi sebuah Pesan Darurat Cerdas yang dikirimkan melalui SMS ke kontak yang telah ditentukan, memberikan informasi situasional yang krusial bagi penolong.

---

## Anggota Kelompok 2

| Nama Lengkap | Nomor Induk Mahasiswa (NIM) |
| :--- | :--- |
| Aswalia Novitriasari | 5027231012 |
| Agnes Zenobia Griselda Petrina | 5027231034 |
| Fadlillah Cantika Sari Hermawan | 5027231042 |
| Rafika Az Zahra Kusumastuti | 5027231050 |
| Nisrina Atiqah Dwiputri Ridzki | 5027231075 |
| Syela Zeruya Tandi Lalong | 5027231076 |

---

## Teknologi yang Digunakan

*   **Bahasa:** Kotlin
*   **Arsitektur:** MVVM (Model-View-ViewModel)
*   **Platform:** Android Native (XML Layouts)
*   **Jaringan:** Retrofit & OkHttp
*   **Database Lokal:** Room Persistence Library
*   **Layanan AI:** Google Cloud Vision API
*   **Kontrol Versi:** Git & GitHub

---

## Panduan Setup Proyek

1.  Pastikan Git sudah ter-install di komputermu.
2.  Buka terminal atau Git Bash, lalu clone repository ini dengan perintah:
    ```bash
    git clone https://github.com/Nopitrasari29/FinalProject-Pemmob-SmartAlert.git
    ```
3.  Buka proyek yang sudah di-clone melalui Android Studio (`File > Open...`).
4.  Tunggu hingga proses **Gradle Sync** selesai. Proyek siap untuk dikembangkan.

---

## **Alur Kerja Pengembangan (Git Workflow)**

Untuk menjaga integritas kode dan memastikan kolaborasi berjalan lancar, seluruh proses pengembangan **WAJIB** mengikuti alur kerja *Feature Branching* berikut.

### **Siklus Kerja untuk Setiap Tugas**

Setiap kali akan memulai pengerjaan sebuah fitur atau tugas baru, ikuti 7 langkah berikut:

#### **Langkah 1: Sinkronisasi dengan `main`**
Pastikan kode di komputermu adalah versi terbaru sebelum memulai pekerjaan baru.

```bash
# Pindah ke branch utama
git checkout main

# Ambil semua pembaruan dari server
git pull origin main
```

#### **Langkah 2: Membuat Branch Baru**
Buat "cabang" kerja pribadi untuk tugas yang akan kamu kerjakan. Gunakan format penamaan yang jelas dan deskriptif.

**Format:** `tipe/deskripsi-singkat-tugas`

*   **`tipe`** bisa berupa:
    *   `feature/`: Untuk membuat fitur baru.
    *   `fix/`: Untuk memperbaiki bug/error.
    *   `docs/`: Untuk menambahkan atau memperbaiki dokumentasi.

*   **`deskripsi-singkat-tugas`** adalah penjelasan tugas dalam 2-4 kata yang dipisahkan tanda hubung.

**Contoh Penamaan Branch (disesuaikan dengan tugas masing-masing):**
*   *Frontend Engineer* yang mengerjakan UI pengaturan: `feature/ui-pengaturan-kontak`
*   *Sensor Engineer* yang mengerjakan deteksi guncangan: `feature/sensor-shake-detection`
*   *Network Engineer* yang memperbaiki error API: `fix/api-json-parsing-error`

**Perintah untuk membuat branch:**
```bash
# Contoh untuk tugas membuat database
git checkout -b feature/database-room-setup
```

#### **Langkah 3: Proses Pengembangan (Coding & Commit)**
Lakukan proses coding untuk menyelesaikan tugasmu di dalam branch ini. Simpan pekerjaanmu secara berkala dengan `commit`.

```bash
# Menambahkan semua file yang diubah
git add .

# Menyimpan perubahan dengan pesan yang jelas
git commit -m "feat: Implement Room Database for contact storage"
```

#### **Langkah 4: Push Branch ke GitHub**
Setelah beberapa kali `commit`, unggah branch kerjamu ke server GitHub. Ini tidak akan mengubah `main`.

```bash
# Ganti nama branch sesuai dengan yang Anda buat
git push origin feature/database-room-setup
```

#### **Langkah 5: Membuat Pull Request (PR)**
Jika tugasmu di branch tersebut sudah selesai 100%, ajukan untuk digabungkan ke `main`.

1.  Buka halaman repository proyek di GitHub.
2.  Kamu akan melihat notifikasi untuk membuat **Pull Request**. Klik tombol tersebut.
3.  Beri judul yang jelas dan deskripsi singkat mengenai pekerjaan yang telah kamu selesaikan.
4.  Klik **"Create Pull Request"**.

#### **Langkah 6: Review dan Merge**
Seorang *reviewer* akan memeriksa kodemu. Jika disetujui, Pull Request akan di-**Merge**. Setelah di-merge, kodemu resmi menjadi bagian dari `main` branch.

#### **Langkah 7: Membersihkan (Cleanup)**
Setelah branch-mu berhasil di-merge, kembali ke komputermu untuk membersihkan branch lama yang sudah tidak terpakai.

```bash
# Kembali ke branch utama
git checkout main

# Hapus branch lama dari komputermu
git branch -d feature/database-room-setup
```

Ulangi siklus ini untuk setiap tugas baru.
