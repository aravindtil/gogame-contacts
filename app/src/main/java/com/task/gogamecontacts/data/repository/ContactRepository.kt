package com.task.gogamecontacts.data.repository

import com.task.gogamecontacts.ContactsApplication
import com.task.gogamecontacts.ui.base.BaseRepository
import com.task.gogamecontacts.data.repository.dao.ContactDao
import com.task.gogamecontacts.database.ContactsDatabase
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
object ContactRepository : BaseRepository() {

    private val contactDao : ContactDao = ContactsDatabase.getInstance(ContactsApplication.instance).contactDao()

    fun saveContact(contact: Contact) {
        contactDao.saveContact(contact)
    }

    fun getContact(contactId: Int): Contact {
        return contactDao.getContact(contactId)
    }

    fun getContact(phone: String): Contact? {
        return contactDao.getContact(phone)
    }

    fun deleteContact(contactId: Int) {
        contactDao.deleteContact(contactId)
    }
}