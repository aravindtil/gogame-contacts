package com.task.gogamecontacts.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.gogamecontacts.data.model.Contact
import com.task.gogamecontacts.data.repository.GroupDetailRepository

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class GroupDetailViewModel internal constructor() : GroupViewModel() {

    fun getAssignedContacts(groupId: Int): LiveData<List<Contact>> {
        return GroupDetailRepository.getAssignedContacts(groupId)
    }

    class GroupDetailViewModelFactory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GroupDetailViewModel() as T
        }
    }
}