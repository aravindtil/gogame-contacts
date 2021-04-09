package com.task.gogamecontacts.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.gogamecontacts.databinding.ItemPictureActionBinding
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener

class PictureActionAdapter(private var optionList: List<String>, private val onOptionChosen: (String) -> Unit) : RecyclerView.Adapter<PictureActionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val brandItemBinding = ItemPictureActionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(brandItemBinding, brandItemBinding.root)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(optionList[position])

    inner class ViewHolder(val binding: ItemPictureActionBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            binding.apply {
                data = item
                executePendingBindings()
            }
            itemView.setOnSingleClickListener {
                onOptionChosen(item)
            }
        }
    }
}