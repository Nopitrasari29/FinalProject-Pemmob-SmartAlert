package com.example.fppemmob_smartalertapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel berfungsi sebagai jembatan antara UI dan lapisan data (Repository).
 *
 * Tanggung jawabnya:
 * 1. Menyediakan data yang sudah siap ditampilkan untuk UI (dalam bentuk LiveData).
 * 2. Menerima input/aksi dari UI (seperti "tambah kontak").
 * 3. Memanggil fungsi di Repository untuk menjalankan logika bisnis.
 * 4. Bertahan dari perubahan konfigurasi (seperti rotasi layar), sehingga data tidak hilang.
 *
 * @param repository Sebuah instance dari ContactRepository yang akan digunakan untuk mengakses data.
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    // Mengonversi Flow<List<ContactEntity>> dari Repository menjadi LiveData<List<ContactEntity>>.
    // LiveData adalah komponen yang bisa "diamati" oleh UI. Setiap kali data di database berubah,
    // LiveData ini akan otomatis memberitahu UI untuk memperbarui tampilannya.
    val allContacts: LiveData<List<ContactEntity>> = repository.allContacts.asLiveData()

    /**
     * Fungsi untuk memasukkan kontak baru ke dalam database.
     * Fungsi ini akan dipanggil dari UI (misalnya, dari sebuah Fragment) saat pengguna
     * menekan tombol "Simpan" setelah mengisi data kontak baru.
     *
     * viewModelScope.launch memastikan bahwa operasi insert ke repository (yang merupakan
     * suspend function) dijalankan di background thread (Coroutine), sehingga tidak
     * memblokir atau membuat UI menjadi macet.
     *
     * @param contact Objek ContactEntity yang akan disimpan.
     */
    fun insert(contact: ContactEntity) = viewModelScope.launch {
        repository.insert(contact)
    }

    /**
     * Fungsi untuk menghapus kontak dari database.
     * Fungsi ini akan dipanggil dari UI, misalnya saat pengguna melakukan swipe untuk
     * menghapus item kontak dari daftar.
     *
     * @param contact Objek ContactEntity yang akan dihapus.
     */
    fun delete(contact: ContactEntity) = viewModelScope.launch {
        repository.delete(contact)
    }
}

/**
 * ViewModelProvider.Factory adalah sebuah kelas "pabrik" yang memberitahu sistem Android
 * bagaimana cara membuat instance (objek) dari ContactViewModel.
 *
 * Kita membutuhkan kelas pabrik ini karena ContactViewModel memiliki parameter
 * di constructor-nya (yaitu 'repository'), sehingga ia tidak bisa dibuat
 * secara langsung oleh sistem. Kita harus memberikannya secara manual.
 *
 * @param repository Instance dari ContactRepository yang akan di-supply ke ContactViewModel.
 */
class ContactViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Memeriksa apakah kelas yang diminta adalah ContactViewModel
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            // Jika ya, buat dan kembalikan instance ContactViewModel dengan repository yang sudah disediakan.
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }
        // Jika kelas yang diminta bukan ContactViewModel, lemparkan error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}