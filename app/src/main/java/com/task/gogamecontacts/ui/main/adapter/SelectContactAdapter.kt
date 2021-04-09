package com.task.gogamecontacts.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.gogamecontacts.databinding.ItemSelectContactBinding
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class SelectContactAdapter(
    private var contactList: List<Contact>,
    private val groupId: Int
) : RecyclerView.Adapter<SelectContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val brandItemBinding =
            ItemSelectContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(brandItemBinding, brandItemBinding.root)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun submitList(data: List<Contact>) {
        contactList = data
        contactList.forEach {
            it.selected = it.groupId == groupId
        }
        notifyDataSetChanged()
    }

    fun getSelectedList(): List<Contact> {
        return contactList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(contactList[position])

    inner class ViewHolder(val binding: ItemSelectContactBinding, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: Contact) {
            binding.apply {
                contact = item
                executePendingBindings()
                selected.setOnCheckedChangeListener { buttonView, isChecked ->
                    onSelection(item)
                }
            }
            itemView.setOnClickListener {
                binding.selected.isChecked = !binding.selected.isChecked
            }
        }

        fun onSelection(item: Contact) {
            item.selected = !item.selected
            item.groupId = if (item.selected) groupId
            else -1
        }
    }
}