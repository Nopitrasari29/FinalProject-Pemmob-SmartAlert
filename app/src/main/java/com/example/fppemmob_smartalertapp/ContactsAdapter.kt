package com.example.fppemmob_smartalertapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fppemmob_smartalertapp.databinding.ItemContactBinding

/**
 * Adapter untuk menampilkan daftar kontak darurat di RecyclerView.
 * Menggunakan ListAdapter untuk efisiensi update data.
 *
 * @param onDeleteClick Lambda function yang dipanggil saat tombol sampah diklik.
 */
class ContactsAdapter(private val onDeleteClick: (ContactEntity) -> Unit) :
    ListAdapter<ContactEntity, ContactsAdapter.ContactViewHolder>(ContactDiffCallback()) {

    // Membuat ViewHolder baru saat dibutuhkan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Menggunakan ViewBinding untuk menghubungkan layout item_contact.xml
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    // Mengisi data ke dalam tampilan (Binding Data)
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, onDeleteClick)
    }

    /**
     * ViewHolder: Memegang referensi ke tampilan setiap item.
     */
    class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactEntity, onDeleteClick: (ContactEntity) -> Unit) {
            // Set Nama dan Nomor
            binding.tvContactName.text = contact.name
            binding.tvContactPhone.text = contact.phoneNumber

            // Set Inisial (Huruf Pertama Nama, misal "Dad" -> "D")
            val initial = contact.name.firstOrNull()?.toString()?.uppercase() ?: "?"
            binding.tvInitial.text = initial

            // Set aksi klik tombol Hapus
            binding.btnDelete.setOnClickListener {
                onDeleteClick(contact)
            }
        }
    }

    /**
     * DiffUtil: Membandingkan data lama dan baru untuk menentukan animasi update.
     */
    class ContactDiffCallback : DiffUtil.ItemCallback<ContactEntity>() {
        override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
            // Cek apakah ID-nya sama (apakah ini item yang sama?)
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
            // Cek apakah isinya (nama/nomor) ada yang berubah?
            return oldItem == newItem
        }
    }
}