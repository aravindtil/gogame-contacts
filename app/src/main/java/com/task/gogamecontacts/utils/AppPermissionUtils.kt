package com.task.gogamecontacts.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.task.gogamecontacts.ui.base.BaseActivity


/**
 * Created by Aravindharaj Natarajan on 20/01/19.
 */
object AppPermissionUtils {

    const val PERMISSION_REQUEST_CODE = 7999
    const val PERMISSION_GRANTED = 1
    const val PERMISSION_ASK = 2
    const val PERMISSION_PREVIOUSLY_DISABLED = 3
    const val NEVER_SHOW_AGAIN = 4
    /*
    * Check if version is marshmallow and above.
    * Used in deciding to ask runtime permission
    * */
    private fun shouldAskPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun shouldAskPermission(activity: BaseActivity, permission: String): Boolean {
        if (shouldAskPermission()) {
            val permissionResult = ActivityCompat.checkSelfPermission(activity, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    fun checkPermission(activity: BaseActivity, permission: String, callback: (value: Int) -> Unit) {
        /*
        * If permission is not granted
        * */
        if (shouldAskPermission(activity, permission)) {
            /*
            * If permission denied previously
            * */
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity.shouldShowRequestPermissionRationale(permission)
                    } else {
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                    }) {
                requestPermission(activity, permission)
                callback(PERMISSION_PREVIOUSLY_DISABLED)
            } else {
                /*
                * Permission denied or first time requested
                * */
                if (PreferenceUtil.isFirstTimeAskingPermission(activity, permission)) {
                    PreferenceUtil.firstTimeAskingPermission(activity, permission, false)
                    requestPermission(activity, permission)
                    callback(PERMISSION_ASK)
                } else {
                    /*
                    * Handle the feature without permission or ask user to manually allow permission
                    * */
                    callback(NEVER_SHOW_AGAIN)
                }
            }
        } else {
            callback(PERMISSION_GRANTED)
        }
    }

    private fun requestPermission(activity: BaseActivity, permission: String) {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                PERMISSION_REQUEST_CODE
        )
    }

    fun openPermissionsScreen(activity: BaseActivity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivityForResult(intent, PERMISSION_REQUEST_CODE)
    }

    class PreferenceUtil {
        companion object {
            fun firstTimeAskingPermission(context: Context, permission: String, isFirstTime: Boolean) {
                val sharedPreference = context.getSharedPreferences(permission, Context.MODE_PRIVATE)
                sharedPreference.edit().putBoolean(permission, isFirstTime).apply()
            }

            fun isFirstTimeAskingPermission(context: Context, permission: String): Boolean {
                return context.getSharedPreferences(permission, Context.MODE_PRIVATE).getBoolean(permission, true)
            }
        }
    }
}