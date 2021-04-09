package com.task.gogamecontacts.ui.main.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.task.gogamecontacts.R
import com.task.gogamecontacts.data.model.Contact
import com.task.gogamecontacts.databinding.ActivitySearchContactBinding
import com.task.gogamecontacts.ui.base.BaseActivity
import com.task.gogamecontacts.ui.main.adapter.ContactAdapter
import com.task.gogamecontacts.ui.main.viewmodel.MainViewModel
import com.task.gogamecontacts.utils.IntentTools
import com.task.gogamecontacts.utils.delegates.viewBinding
import com.task.gogamecontacts.utils.ktxextensions.hide
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.utils.ktxextensions.show

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class SearchContactActivity : BaseActivity() {

    private val binding by viewBinding(ActivitySearchContactBinding::inflate)
    private val viewModel: MainViewModel by viewModels { MainViewModel.MainViewModelFactory() }
    private val contacts: ArrayList<Contact> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            noData.noIv.setImageResource(R.drawable.ic_no_contacts)
            noData.noText.text = getString(R.string.no_contacts)
            back.setOnSingleClickListener {
                finish()
            }
            adapter = ContactAdapter(listOf()) {
                IntentTools.showContactScreen(this@SearchContactActivity, it._id)
            }
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(s: String): Boolean =
                    false

                override fun onQueryTextChange(s: String): Boolean {
                    onSearch(s)
                    return false
                }
            })
            search.setOnCloseListener {
                onSearchClosed()
                false
            }
        }
        viewModel.contacts.observe(this) {
            contacts.clear()
            contacts.addAll(it)
            onSearch(binding.search.query.toString())
        }
    }

    fun onSearch(s: String) {
        if (s.isEmpty()) onSearchClosed()
        else
            setData(contacts.filter {
                it.name.toLowerCase().trim().contains(s.toLowerCase().trim())
            })
    }

    fun onSearchClosed() {
        setData(listOf())
    }

    fun setData(it: List<Contact>) {
        binding.apply {
            groupCount.text = "${it.size} found"
            (adapter as ContactAdapter).submitList(it)
            when {
                it.isNotEmpty() -> {
                    noData.root.hide()
                }
                it.isEmpty() -> {
                    noData.root.show()
                }
            }
        }
    }
}