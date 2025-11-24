package com.example.fppemmob_smartalertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsMapAdapter(
    private val contactsList: List<ContactModel>,
    // Callback saat tombol telepon diklik
    private val onCallButtonClicked: (ContactModel) -> Unit
) : RecyclerView.Adapter<ContactsMapAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvContactName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvContactStatus)
        val btnCall: ImageButton = itemView.findViewById(R.id.btnAlertContact)

        fun bind(contact: ContactModel) {
            tvName.text = contact.name
            tvStatus.text = "${contact.phoneNumber} • ${contact.status}"
            btnCall.setOnClickListener {
                onCallButtonClicked(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Perhatikan: Kita menggunakan layout item_contact_map.xml yang akan kita buat di langkah 4
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_map, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int = contactsList.size
}