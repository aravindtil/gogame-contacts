package com.task.gogamecontacts.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.gogamecontacts.databinding.ItemContactBinding
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class ContactAdapter(
    private var contactList: List<Contact>,
    private val onContactChosen: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val brandItemBinding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(brandItemBinding, brandItemBinding.root)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun submitList(data: List<Contact>) {
        contactList = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(contactList[position])

    inner class ViewHolder(val binding: ItemContactBinding, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: Contact) {
            binding.apply {
                contact = item
                executePendingBindings()
            }
            itemView.setOnSingleClickListener {
                onContactChosen(item)
            }
        }
    }
}