package com.task.gogamecontacts.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.gogamecontacts.databinding.ItemGroupBinding
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.data.model.Group

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupAdapter(
    private var groupList: List<Group>,
    private val onGroupChosen: (Group) -> Unit
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val brandItemBinding =
            ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(brandItemBinding, brandItemBinding.root)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    fun submitList(data: List<Group>) {
        groupList = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(groupList[position])

    inner class ViewHolder(val binding: ItemGroupBinding, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: Group) {
            binding.apply {
                group = item
                executePendingBindings()
            }
            itemView.setOnSingleClickListener {
                onGroupChosen(item)
            }
        }
    }
}