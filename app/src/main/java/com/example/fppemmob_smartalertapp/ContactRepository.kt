package com.example.fppemmob_smartalertapp

import kotlinx.coroutines.flow.Flow

/**
 * Repository yang menjadi perantara antara lapisan UI/ViewModel dan sumber data (DAO).
 *
 * Tujuan utama Repository adalah untuk mengisolasi sumber data. ViewModel tidak perlu tahu
 * apakah data berasal dari database lokal (Room), jaringan (API), atau cache.
 * Repository menyediakan API yang bersih untuk mengakses data.
 *
 * @property contactDao Objek DAO untuk berinteraksi langsung dengan database Room.
 */
class ContactRepository(private val contactDao: ContactDao) {

    /**
     * Properti yang mengekspos daftar semua kontak dari database.
     * Menggunakan Flow<List<ContactEntity>> memungkinkan UI untuk secara reaktif
     * mengamati perubahan data. Setiap kali ada data yang ditambahkan, diubah, atau dihapus
     * di tabel, Flow ini akan otomatis memancarkan daftar data yang baru.
     */
    val allContacts: Flow<List<ContactEntity>> = contactDao.getAllContacts()

    /**
     * Fungsi suspend untuk memasukkan data kontak baru ke dalam database.
     *
     * Kata kunci 'suspend' menandakan bahwa fungsi ini harus dipanggil dari dalam
     * sebuah Coroutine atau fungsi suspend lainnya. Ini memastikan operasi database
     * yang berpotensi memakan waktu tidak memblokir thread utama aplikasi.
     *
     * @param contact Objek ContactEntity yang akan disimpan.
     */
    suspend fun insert(contact: ContactEntity) {
        contactDao.insertContact(contact)
    }

    /**
     * Fungsi suspend untuk menghapus data kontak dari database.
     *
     * Sama seperti insert, fungsi ini juga bersifat suspend untuk memastikan
     * operasi berjalan di background thread.
     *
     * @param contact Objek ContactEntity yang akan dihapus.
     */
    suspend fun delete(contact: ContactEntity) {
        contactDao.deleteContact(contact)
    }
}