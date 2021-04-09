package com.task.gogamecontacts.ui.main.view

import android.os.Bundle
import androidx.activity.viewModels
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.base.BaseActivity
import com.task.gogamecontacts.databinding.ActivityGroupAssignBinding
import com.task.gogamecontacts.ui.main.adapter.SelectContactAdapter
import com.task.gogamecontacts.utils.delegates.viewBinding
import com.task.gogamecontacts.ui.main.viewmodel.GroupAssignViewModel
import com.task.gogamecontacts.utils.ktxextensions.hide
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.utils.ktxextensions.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupAssignActivity : BaseActivity() {
    private val binding by viewBinding(ActivityGroupAssignBinding::inflate)
    private val gViewModel: GroupAssignViewModel by viewModels { GroupAssignViewModel.GroupAssignViewModelFactory() }
    private val groupId by extraNotNull<Int>("groupId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            noData.noIv.setImageResource(R.drawable.ic_no_contacts)
            noData.noText.text = getString(R.string.no_fav_contacts)
            lifecycleOwner = this@GroupAssignActivity
            viewModel = gViewModel
            adapter = SelectContactAdapter(listOf(), groupId)
            back.setOnSingleClickListener {
                finish()
            }
            bottomBar.save.setOnSingleClickListener {
                launch {
                    async(Dispatchers.IO) {
                        gViewModel.assignContacts((binding.adapter as SelectContactAdapter).getSelectedList())
                    }.await().let {
                        finish()
                    }
                }
            }
        }
        gViewModel.fetchGroup(groupId)
        gViewModel.getAssignableContacts(groupId).observe(this) {
            binding.apply {
                (adapter as SelectContactAdapter).submitList(it)
                when {
                    it.isNotEmpty() -> {
                        groupCount.text = "${it.size} People(s)"
                        noData.root.hide()
                    }
                    it.isEmpty() -> {
                        groupCount.text = "0 People(s)"
                        noData.root.show()
                    }
                }
            }
        }
    }
}