package com.task.gogamecontacts.data.repository

import androidx.lifecycle.LiveData
import com.task.gogamecontacts.ContactsApplication
import com.task.gogamecontacts.ui.base.BaseRepository
import com.task.gogamecontacts.data.repository.dao.GroupAssignDao
import com.task.gogamecontacts.database.ContactsDatabase
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
object GroupAssignRepository: BaseRepository() {
    private val groupAssignDao : GroupAssignDao = ContactsDatabase.getInstance(ContactsApplication.instance).groupAssignDao()

    fun getAssignableContacts(groupId: Int): LiveData<List<Contact>> {
        return groupAssignDao.getAssignableContacts(groupId)
    }

    fun assignContacts(data: List<Contact>) {
        groupAssignDao.assignContacts(data)
    }
}