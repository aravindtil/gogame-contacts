package com.task.gogamecontacts.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.base.BaseViewModel
import com.task.gogamecontacts.ui.base.ScreenState
import com.task.gogamecontacts.data.model.Contact
import com.task.gogamecontacts.data.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactViewModel internal constructor() : BaseViewModel() {

    private val _contactViewState: MutableLiveData<ScreenState<ContactViewState>> =
        MutableLiveData()
    val contactViewState: LiveData<ScreenState<ContactViewState>>
        get() = _contactViewState

    var contact: MutableLiveData<Contact> = MutableLiveData(Contact())

    fun saveContact(contactId: Int) {
        launch {
            async(Dispatchers.IO) {
                contact.value?.let {
                    if (!it.favourite)
                        it.groupId = -1
                    ContactRepository.saveContact(it)
                }
            }.await().let {
                _contactViewState.postValue(
                    ScreenState.Render(
                        if (contactId == -1) ContactViewState.ContactAdded
                        else ContactViewState.ContactEdited
                    )
                )
            }
        }
    }

    fun onSaveClick(contactId: Int) {
        val contact = contact.`value`
        when {
            contact?.name.isNullOrEmpty() -> {
                _contactViewState.value =
                    ScreenState.Render(
                        ContactViewState.Error1(
                            R.string.name_error
                        )
                    )
                return
            }
            contact?.phoneNumber.isNullOrEmpty() -> {
                _contactViewState.value =
                    ScreenState.Render(
                        ContactViewState.Error1(
                            R.string.phone_error
                        )
                    )
                return
            }
            else -> {
                if (contactId == -1)
                    launch {
                        async(Dispatchers.IO) {
                            return@async contact?.phoneNumber?.let { fetchContact(it) }
                        }.await().let {
                            if (it == null) {
                                _contactViewState.postValue(
                                    ScreenState.Render(
                                        ContactViewState.ValidationSuccess
                                    )
                                )
                            } else {
                                _contactViewState.postValue(
                                    ScreenState.Render(
                                        ContactViewState.Error1(
                                            R.string.dup_contact
                                        )
                                    )
                                )
                            }
                        }
                    }
                else
                    _contactViewState.postValue(
                        ScreenState.Render(
                            ContactViewState.ValidationSuccess
                        )
                    )
            }
        }
    }

    fun fetchContact(contactId: Int) {
        launch {
            async(Dispatchers.IO) {
                return@async ContactRepository.getContact(contactId)
            }.await().let {
                contact.postValue(it)
            }
        }
    }

    fun fetchContact(phone: String): Contact? {
        return ContactRepository.getContact(phone)
    }

    fun onDelete(contactId: Int) {
        launch {
            async(Dispatchers.IO) {
                contact.value?.let { ContactRepository.deleteContact(contactId) }
            }.await().let {
                _contactViewState.postValue(
                    ScreenState.Render(
                        ContactViewState.ContactDeleted
                    )
                )
            }
        }
    }

    class ContactViewModelFactory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContactViewModel() as T
        }
    }
}