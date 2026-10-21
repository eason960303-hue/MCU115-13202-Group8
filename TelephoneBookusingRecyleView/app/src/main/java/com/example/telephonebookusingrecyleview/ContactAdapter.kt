package com.example.telephonebookusingrecyleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val fullContactList: MutableList<Contact>,
    private val onDeleteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var displayedContactList: MutableList<Contact> = fullContactList.toMutableList()
    private var currentQuery: String = ""

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = displayedContactList[position]
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone
        holder.tvEmail.text = contact.email

        holder.btnDelete.setOnClickListener {
            onDeleteClick(contact)
        }
    }

    override fun getItemCount(): Int = displayedContactList.size

    fun filter(query: String) {
        currentQuery = query
        applyFilter()
    }

    private fun applyFilter() {
        if (currentQuery.isBlank()) {
            displayedContactList = fullContactList.toMutableList()
        } else {
            val q = currentQuery.trim().lowercase()
            displayedContactList = fullContactList.filter { contact ->
                contact.name.lowercase().contains(q) ||
                contact.phone.lowercase().contains(q) ||
                contact.email.lowercase().contains(q)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun addContact(contact: Contact) {
        fullContactList.add(0, contact)
        applyFilter()
    }

    fun removeContact(contact: Contact) {
        fullContactList.removeAll { it.id == contact.id }
        applyFilter()
    }

    fun getDisplayedItemCount(): Int = displayedContactList.size
}
