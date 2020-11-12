package ru.focusstart.kireev.homeworkroom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_item.view.*
import ru.focusstart.kireev.homeworkroom.R
import ru.focusstart.kireev.homeworkroom.database.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var contacts: List<Contact> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.textViewName
        private val textViewPhone: TextView = itemView.textViewPhone

        fun bind(contact: Contact) {
            textViewName.text = contact.name
            val sb = StringBuilder()
            for (phones in contact.phones) {
                sb.append(phones).append("\n")
            }
            textViewPhone.text = sb.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}