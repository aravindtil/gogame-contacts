package com.task.gogamecontacts.ui.main.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.main.dialog.PictureActionDialog
import com.task.gogamecontacts.ui.base.BaseActivity
import com.task.gogamecontacts.ui.base.ScreenState
import com.task.gogamecontacts.databinding.ActivityContactBinding
import com.task.gogamecontacts.utils.delegates.viewBinding
import com.task.gogamecontacts.utils.ktxextensions.setOnSingleClickListener
import com.task.gogamecontacts.utils.AppPermissionUtils
import com.task.gogamecontacts.utils.ImageConstants
import com.task.gogamecontacts.utils.ImageFilePath
import com.task.gogamecontacts.utils.ImageUtils
import com.task.gogamecontacts.utils.ImageUtils.chooseImageFromGallery
import com.task.gogamecontacts.utils.ImageUtils.dispatchTakePictureIntent
import com.task.gogamecontacts.ui.main.viewmodel.ContactViewModel
import com.task.gogamecontacts.ui.main.viewmodel.ContactViewState
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch

class ContactActivity : BaseActivity(), PictureActionDialog.Callback {

    private val binding by viewBinding(ActivityContactBinding::inflate)
    private var imageUri: Uri? = null
    private val viewModel: ContactViewModel by viewModels { ContactViewModel.ContactViewModelFactory() }
    private val contactId by extraNotNull<Int>("contactId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.contactViewState.observe(::getLifecycle, ::updateUi)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.contactId = contactId
        if (contactId != -1)
            viewModel.fetchContact(contactId)
        binding.profile.setOnSingleClickListener {
            val dialog =
                PictureActionDialog.newInstance(data = !(viewModel.contact.value?.profilePicture.isNullOrEmpty()))
            dialog.setCallback(this)
            dialog.show(supportFragmentManager, "picture_action_dialog")
        }
        binding.save.setOnSingleClickListener {
            viewModel.onSaveClick(contactId)
        }
        binding.layoutBottomBar.delete.setOnSingleClickListener {
            viewModel.onDelete(contactId)
        }
        binding.close.setOnSingleClickListener {
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppPermissionUtils.PERMISSION_REQUEST_CODE &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            chooseImageFromGallery(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageConstants.ACTIVITY_REQUEST_CODE_PICK_IMAGE) {
            val selectedImage = data?.data
            if (selectedImage != null)
                ImageUtils.cropImage(this, selectedImage)
        } else if (requestCode == ImageConstants.ACTIVITY_REQUEST_CODE_REQUEST_IMAGE_CAPTURE) {
            if (resultCode == -1) {
                imageUri?.let {
                    ImageUtils.cropImage(this, it)
                }
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return
                }
                val resultUri: Uri? = UCrop.getOutput(data)
                resultUri?.run {
                    val imagePath = ImageFilePath.getPath(baseContext, resultUri) ?: ""
                    viewModel.contact.value?.profilePicture = "file://$imagePath"
                }
            }
        }
    }

    override fun optionChosen(string: String) {
        when (string) {
            getString(R.string.remove_photo) -> {
                viewModel.contact.value?.profilePicture = ""
            }
            getString(R.string.take_photo) -> {
                dispatchTakePictureIntent(this) {
                    imageUri = it
                }
            }
            getString(R.string.choose_photo) -> {
                chooseImageFromGallery(this)
            }
        }
    }

    private fun updateUi(screenState: ScreenState<ContactViewState>) {
        when (screenState) {
            is ScreenState.Render -> {
                processViewState(screenState.renderState)
            }
        }
    }

    private fun processViewState(renderState: ContactViewState) {
        when (renderState) {
            is ContactViewState.ValidationSuccess -> {
                viewModel.saveContact(contactId)
            }
            is ContactViewState.ContactAdded -> {
                launch {
                    Toast.makeText(this@ContactActivity, R.string.contact_added, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            is ContactViewState.ContactEdited -> {
                launch {
                    Toast.makeText(this@ContactActivity, R.string.contact_edited, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            is ContactViewState.ContactDeleted -> {
                launch {
                    Toast.makeText(this@ContactActivity, R.string.contact_deleted, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            is ContactViewState.Error1 -> {
                Toast.makeText(this, getString(renderState.error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}