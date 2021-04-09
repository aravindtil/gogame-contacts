package com.task.gogamecontacts.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.gogamecontacts.databinding.ItemContactBinding
import com.task.gogamecontacts.databinding.ItemLetterBinding
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class MainContactAdapter(
    private var contactList: List<Any>,
    private val onContactChosen: (Contact) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val binding =
                    ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return LetterViewHolder(binding, binding.root)
            }
            else -> {
                val binding =
                    ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ContactViewHolder(binding, binding.root)
            }
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (contactList[position]) {
            is String -> 0
            else -> 1
        }
    }

    fun submitList(data: List<Any>) {
        contactList = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> (holder as LetterViewHolder).bind(contactList[position] as String)
            else -> (holder as ContactViewHolder).bind(contactList[position] as Contact)
        }
    }

    inner class LetterViewHolder(val binding: ItemLetterBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            binding.apply {
                data = item
                executePendingBindings()
            }
        }
    }

    inner class ContactViewHolder(val binding: ItemContactBinding, itemView: View) :
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