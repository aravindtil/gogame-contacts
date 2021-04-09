package com.task.gogamecontacts.ui.main.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.task.gogamecontacts.R
import com.task.gogamecontacts.databinding.DialogPictureActionBinding
import com.task.gogamecontacts.ui.main.adapter.PictureActionAdapter
import com.task.gogamecontacts.utils.delegates.autoCleared

/**
 * Created by Aravindharaj Natarajan on 08-04-2021.
 */
class PictureActionDialog : BottomSheetDialogFragment(), DialogInterface.OnDismissListener {
    private var callback: Callback? = null
    private var binding by autoCleared<DialogPictureActionBinding>()
    private val editMode by extraNotNull<Boolean>("data")

    inline fun <reified T : Any> extraNotNull(key: String, default: T? = null) = lazy {
        val value = arguments?.get(key)
        requireNotNull(if (value is T) value else default) { key }
    }

    interface Callback {
        fun optionChosen(string: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_picture_action,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = PictureActionAdapter(
            if (editMode) resources.getStringArray(R.array.picture_actions_1).toList()
            else resources.getStringArray(R.array.picture_actions_2).toList()
        ) {
            callback?.optionChosen(it)
            dismiss()
        }
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    companion object {
        fun newInstance(data: Boolean): PictureActionDialog {
            val fragment = PictureActionDialog()
            val args = Bundle()
            args.putBoolean("data", data)
            fragment.arguments = args
            return fragment
        }
    }
}