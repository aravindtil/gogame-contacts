package com.task.gogamecontacts.data.repository

import androidx.lifecycle.LiveData
import com.task.gogamecontacts.ContactsApplication
import com.task.gogamecontacts.database.ContactsDatabase
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
object MainRepository {

    private val contactDao = ContactsDatabase.getInstance(ContactsApplication.instance).contactDao()

    fun getAllContacts(): LiveData<List<Contact>> {
        return contactDao.getAllContacts()
    }
}