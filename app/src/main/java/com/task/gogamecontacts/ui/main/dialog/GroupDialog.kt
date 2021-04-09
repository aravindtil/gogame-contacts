package com.task.gogamecontacts.ui.main.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.base.BaseBottomDialog
import com.task.gogamecontacts.ui.base.ScreenState
import com.task.gogamecontacts.databinding.DialogGroupBinding
import com.task.gogamecontacts.utils.delegates.autoCleared
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.ui.main.viewmodel.GroupViewModel
import com.task.gogamecontacts.ui.main.viewmodel.GroupViewState
import kotlinx.coroutines.launch

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupDialog : BaseBottomDialog(), DialogInterface.OnDismissListener {
    private var mCallback: Callback? = null
    private var binding by autoCleared<DialogGroupBinding>()
    private val groupId by extraNotNull<Int>("data")
    private lateinit var gViewModel: GroupViewModel

    interface Callback {
        fun onChange()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gViewModel = ViewModelProvider(
            this,
            GroupViewModel.GroupViewModelFactory()
        ).get(GroupViewModel::class.java)
        gViewModel.groupViewState.observe(::getLifecycle, ::updateUi)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_group,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (groupId != -1)
            gViewModel.fetchGroup(groupId)
        binding.apply {
            lifecycleOwner = this@GroupDialog
            viewModel = gViewModel
            layoutButtons.negativeButton.text = "Cancel"
            layoutButtons.positiveButton.text = "OK"
            layoutButtons.negativeButton.setOnSingleClickListener {
                dismiss()
            }
            layoutButtons.positiveButton.setOnSingleClickListener {
                gViewModel.onSaveClick(groupId)
            }
        }
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
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
            is GroupViewState.ValidationSuccess -> {
                gViewModel.saveGroup(groupId)
            }
            is GroupViewState.GroupAdded -> {
                launch {
                    Toast.makeText(context, R.string.group_added, Toast.LENGTH_SHORT)
                        .show()
                    dismiss()
                }
            }
            is GroupViewState.GroupEdited -> {
                launch {
                    Toast.makeText(context, R.string.group_edited, Toast.LENGTH_SHORT)
                        .show()
                    mCallback?.onChange()
                    dismiss()
                }
            }
            is GroupViewState.Error1 -> {
                Toast.makeText(context, getString(renderState.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(data: Int): GroupDialog {
            val fragment = GroupDialog()
            val args = Bundle()
            args.putInt("data", data)
            fragment.arguments = args
            return fragment
        }
    }
}