package com.task.gogamecontacts.data.repository

import androidx.lifecycle.LiveData
import com.task.gogamecontacts.ContactsApplication
import com.task.gogamecontacts.ui.base.BaseRepository
import com.task.gogamecontacts.data.repository.dao.GroupDetailDao
import com.task.gogamecontacts.database.ContactsDatabase
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
object GroupDetailRepository: BaseRepository() {
    private val groupDetailDao : GroupDetailDao = ContactsDatabase.getInstance(ContactsApplication.instance).groupDetailDao()

    fun getAssignedContacts(groupId: Int): LiveData<List<Contact>> {
        return groupDetailDao.getAssignedContacts(groupId)
    }

}