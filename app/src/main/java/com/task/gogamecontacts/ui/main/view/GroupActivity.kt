package com.task.gogamecontacts.ui.main.view

import android.os.Bundle
import androidx.activity.viewModels
import com.task.gogamecontacts.R
import com.task.gogamecontacts.databinding.ActivityGroupBinding
import com.task.gogamecontacts.ui.base.BaseActivity
import com.task.gogamecontacts.ui.main.adapter.GroupAdapter
import com.task.gogamecontacts.ui.main.dialog.GroupDialog
import com.task.gogamecontacts.ui.main.viewmodel.GroupViewModel
import com.task.gogamecontacts.utils.IntentTools
import com.task.gogamecontacts.utils.delegates.viewBinding
import com.task.gogamecontacts.utils.ktxextensions.hide
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.utils.ktxextensions.show

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupActivity : BaseActivity() {
    private val binding by viewBinding(ActivityGroupBinding::inflate)
    private val viewModel: GroupViewModel by viewModels { GroupViewModel.GroupViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            noData.noIv.setImageResource(R.drawable.ic_no_group)
            noData.noText.text = getString(R.string.no_groups)
            adapter = GroupAdapter(listOf()) {
                IntentTools.showGroupDetailScreen(activity = this@GroupActivity, groupId = it._id)
            }
            back.setOnSingleClickListener {
                finish()
            }
            fab.setOnSingleClickListener {
                val dialog = GroupDialog.newInstance(data = -1)
                dialog.show(supportFragmentManager, "group_dialog")
            }
        }
        viewModel.groups.observe(this) {
            binding.apply {
                (adapter as GroupAdapter).submitList(it)
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
}