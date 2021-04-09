package com.task.gogamecontacts.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.base.BaseActivity
import com.task.gogamecontacts.ui.base.ScreenState
import com.task.gogamecontacts.ui.base.flattenList
import com.task.gogamecontacts.databinding.ActivityGroupDetailBinding
import com.task.gogamecontacts.utils.delegates.viewBinding
import com.task.gogamecontacts.ui.main.dialog.GroupDialog
import com.task.gogamecontacts.ui.main.adapter.MainContactAdapter
import com.task.gogamecontacts.utils.IntentTools
import com.task.gogamecontacts.ui.main.viewmodel.GroupDetailViewModel
import com.task.gogamecontacts.ui.main.viewmodel.GroupViewState
import com.task.gogamecontacts.utils.ktxextensions.hide
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.utils.ktxextensions.show
import kotlinx.coroutines.launch

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupDetailActivity : BaseActivity(), GroupDialog.Callback {
    private val binding by viewBinding(ActivityGroupDetailBinding::inflate)
    private val gViewModel: GroupDetailViewModel by viewModels { GroupDetailViewModel.GroupDetailViewModelFactory() }
    private val groupId by extraNotNull<Int>("groupId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            noData.noIv.setImageResource(R.drawable.ic_no_contacts)
            noData.noText.text = getString(R.string.no_group_members)
            lifecycleOwner = this@GroupDetailActivity
            viewModel = gViewModel
            adapter = MainContactAdapter(listOf()) {
                IntentTools.showContactScreen(this@GroupDetailActivity, it._id)
            }
            back.setOnSingleClickListener {
                finish()
            }
            bottomBar.addRemove.setOnSingleClickListener {
                IntentTools.showGroupAssignScreen(this@GroupDetailActivity, groupId)
            }
            bottomBar.edit.setOnSingleClickListener {
                val dialog = GroupDialog.newInstance(data = groupId)
                dialog.setCallback(this@GroupDetailActivity)
                dialog.show(supportFragmentManager, "group_dialog")
            }
            bottomBar.delete.setOnSingleClickListener {
                gViewModel.onDelete(groupId)
            }
        }
        gViewModel.groupViewState.observe(::getLifecycle, ::updateUi)
        gViewModel.fetchGroup(groupId)
        gViewModel.getAssignedContacts(groupId).observe(this) {
            binding.apply {
                (adapter as MainContactAdapter).submitList(it.flattenList(resources.getStringArray(R.array.letters).toList()))
                when {
                    it.isNotEmpty() -> {
                        noData.root.hide()
                        groupCount.text = "${it.size} People(s)"
                    }
                    it.isEmpty() -> {
                        groupCount.text = "0 People(s)"
                        noData.root.show()
                    }
                }
            }
        }
    }

    private fun updateUi(screenState: ScreenState<GroupViewState>) {
        when (screenState) {
            is ScreenState.Render -> {
                processViewState(screenState.renderState)
            }
        }
    }

    private fun processViewState(renderState: GroupViewState) {
        when (renderState) {
            is GroupViewState.GroupDeleted -> {
                launch {
                    Toast.makeText(this@GroupDetailActivity, R.string.group_deleted, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            is GroupViewState.Error1 -> {
                Toast.makeText(this, getString(renderState.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onChange() {
        gViewModel.fetchGroup(groupId)
    }
}