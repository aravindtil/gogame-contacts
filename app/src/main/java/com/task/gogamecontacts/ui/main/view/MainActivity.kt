package com.task.gogamecontacts.ui.main.view

import android.os.Bundle
import androidx.activity.viewModels
import com.task.gogamecontacts.R
import com.task.gogamecontacts.databinding.ActivityMainBinding
import com.task.gogamecontacts.ui.base.BaseActivity
import com.task.gogamecontacts.ui.base.flattenList
import com.task.gogamecontacts.ui.main.adapter.ContactAdapter
import com.task.gogamecontacts.ui.main.adapter.MainContactAdapter
import com.task.gogamecontacts.ui.main.viewmodel.MainViewModel
import com.task.gogamecontacts.utils.IntentTools
import com.task.gogamecontacts.utils.delegates.viewBinding
import com.task.gogamecontacts.utils.ktxextensions.hide
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.utils.ktxextensions.show

class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels { MainViewModel.MainViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            noData.noIv.setImageResource(R.drawable.ic_no_contacts)
            noData.noText.text = getString(R.string.no_contacts)
            favAdapter = ContactAdapter(listOf()) {
                IntentTools.showContactScreen(activity = this@MainActivity, contactId = it._id)
            }
            adapter = MainContactAdapter(listOf()) {
                IntentTools.showContactScreen(activity = this@MainActivity, contactId = it._id)
            }
            fab.setOnSingleClickListener {
                IntentTools.showContactScreen(activity = this@MainActivity)
            }
            search.setOnSingleClickListener {
                IntentTools.showSearchScreen(activity = this@MainActivity)
            }
            myGroupsLayout.setOnSingleClickListener {
                IntentTools.showMyGroupsScreen(activity = this@MainActivity)
            }
        }
        viewModel.contacts.observe(this) {
            val fav = it.filter { it.favourite }
            binding.apply {
                search.text = "Search ${it.size} contacts"
                (adapter as MainContactAdapter).submitList(
                    it.flattenList(
                        resources.getStringArray(R.array.letters).toList()
                    )
                )
                (favAdapter as ContactAdapter).submitList(fav)
                when {
                    it.isNotEmpty() -> {
                        search.show()
                        noData.root.hide()
                    }
                    it.isEmpty() -> {
                        search.hide()
                        noData.root.show()
                    }
                }
                when {
                    fav.isNotEmpty() -> {
                        favLayout.show()
                    }
                    fav.isEmpty() -> {
                        favLayout.hide()
                    }
                }
            }
        }
    }
}