package com.example.fppemmob_smartalertapp

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fppemmob_smartalertapp.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    // Panggil ViewModel yang sudah dibuat di Core
    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((requireActivity().application as ContactsApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        val adapter = ContactsAdapter { contact ->
            // Aksi saat tombol hapus diklik
            contactViewModel.delete(contact)
            Toast.makeText(context, "${contact.name} deleted", Toast.LENGTH_SHORT).show()
        }
        binding.rvContacts.adapter = adapter
        binding.rvContacts.layoutManager = LinearLayoutManager(context)

        // Observasi Data (Live Update)
        contactViewModel.allContacts.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }

        // Tombol Tambah Kontak -> Muncul Dialog
        binding.btnAddContact.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_contact, null)
        val builder = AlertDialog.Builder(context).setView(dialogView)
        val dialog = builder.create()

        // Bikin background transparan biar sudutnya melengkung
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val etName = dialogView.findViewById<EditText>(R.id.et_name)
        val etPhone = dialogView.findViewById<EditText>(R.id.et_phone)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_save_contact)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel_dialog)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()

            if (name.isNotBlank() && phone.isNotBlank()) {
                val newContact = ContactEntity(name = name, phoneNumber = phone)
                contactViewModel.insert(newContact)
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Name and Phone must be exist.", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}