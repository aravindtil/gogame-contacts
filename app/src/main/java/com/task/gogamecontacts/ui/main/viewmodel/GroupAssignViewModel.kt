package com.task.gogamecontacts.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.gogamecontacts.data.model.Contact
import com.task.gogamecontacts.data.repository.GroupAssignRepository

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupAssignViewModel internal constructor() : GroupViewModel() {

    fun assignContacts(data: List<Contact>) {
        GroupAssignRepository.assignContacts(data)
    }

    fun getAssignableContacts(groupId: Int): LiveData<List<Contact>> {
        return GroupAssignRepository.getAssignableContacts(groupId)
    }

    class GroupAssignViewModelFactory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GroupAssignViewModel() as T
        }
    }
}