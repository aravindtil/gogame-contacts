package com.task.gogamecontacts.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.task.gogamecontacts.ui.main.view.*

/**
 * Created by Aravindharaj Natarajan on 08-04-2021.
 */
object IntentTools {

    fun showContactScreen(activity: AppCompatActivity, contactId: Int = -1) {
        val intent = Intent(activity, ContactActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("contactId", contactId)
        }
        activity.startActivity(intent)
    }

    fun showMyGroupsScreen(activity: AppCompatActivity) {
        val intent = Intent(activity, GroupActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        activity.startActivity(intent)
    }

    fun showGroupDetailScreen(activity: AppCompatActivity, groupId: Int = -1) {
        val intent = Intent(activity, GroupDetailActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("groupId", groupId)
        }
        activity.startActivity(intent)
    }

    fun showGroupAssignScreen(activity: AppCompatActivity, groupId: Int = -1) {
        val intent = Intent(activity, GroupAssignActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("groupId", groupId)
        }
        activity.startActivity(intent)
    }

    fun showSearchScreen(activity: AppCompatActivity) {
        val intent = Intent(activity, SearchContactActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        activity.startActivity(intent)
    }
}