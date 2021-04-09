package com.task.gogamecontacts.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.gogamecontacts.ui.base.BaseViewModel
import com.task.gogamecontacts.data.model.Contact
import com.task.gogamecontacts.data.repository.MainRepository

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
class MainViewModel internal constructor() : BaseViewModel() {

    var contacts: LiveData<List<Contact>> = MainRepository.getAllContacts()

    class MainViewModelFactory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}