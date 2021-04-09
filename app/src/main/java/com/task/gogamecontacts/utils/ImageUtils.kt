package com.task.gogamecontacts.utils

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.base.BaseActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.yalantis.ucrop.UCrop

object ImageUtils {
    fun dispatchTakePictureIntent(activity: BaseActivity, callback: (uri: Uri) -> Unit) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            var photoFile: File?
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                try {
                    photoFile = createImageFile(activity)
                    val photoURI: Uri = FileProvider.getUriForFile(activity,
                            "com.task.gogamecontacts.provider", photoFile as File)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI)
                    callback(photoURI)
                    activity.startActivityForResult(takePictureIntent, ImageConstants.ACTIVITY_REQUEST_CODE_REQUEST_IMAGE_CAPTURE)
                } catch (ex: Exception) {
                    Toast.makeText(activity, "There was an error while opening camera", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun chooseImageFromGallery(activity: BaseActivity) {
        AppPermissionUtils.checkPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) {
            when (it) {
                AppPermissionUtils.PERMISSION_GRANTED -> {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), ImageConstants.ACTIVITY_REQUEST_CODE_PICK_IMAGE)
                }
                AppPermissionUtils.NEVER_SHOW_AGAIN -> {
                    Toast.makeText(activity, activity.resources.getString(R.string.storage_permission_denied), Toast.LENGTH_SHORT).show()
                    AppPermissionUtils.openPermissionsScreen(activity)
                }
            }
        }
    }

    fun cropImage(context: Activity?, sourceUri: Uri) {
        val destinationUri: Uri
        sourceUri.let { destinationUri = Uri.fromFile(File(context?.cacheDir, context?.contentResolver?.let { it1 -> queryName(it1, it) })) }
        val options = UCrop.Options()
        options.setCompressionQuality(80)
        context?.let { ContextCompat.getColor(it, R.color.teal_700) }?.let { options.setToolbarColor(it) }
        context?.let { ContextCompat.getColor(it, R.color.teal_700) }?.let { options.setStatusBarColor(it) }
        context?.let {
            UCrop.of(sourceUri, destinationUri)
                    .withOptions(options)
                    .start(it)
        }
    }

    private fun queryName(resolver: ContentResolver, uri: Uri): String? {
        val returnCursor: Cursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
        return image
    }
}