package com.task.gogamecontacts.data.repository

import androidx.lifecycle.LiveData
import com.task.gogamecontacts.ContactsApplication
import com.task.gogamecontacts.ui.base.BaseRepository
import com.task.gogamecontacts.data.repository.dao.GroupDao
import com.task.gogamecontacts.database.ContactsDatabase
import com.task.gogamecontacts.data.model.Group

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
object GroupRepository: BaseRepository() {
    private val groupDao : GroupDao = ContactsDatabase.getInstance(ContactsApplication.instance).groupDao()

    fun saveGroup(group: Group) {
        groupDao.saveGroup(group)
    }

    fun getGroup(groupId: Int): Group {
        return groupDao.getGroup(groupId)
    }

    fun getGroup(name: String): Group? {
        return groupDao.getGroup(name)
    }

    fun deleteGroup(groupId: Int) {
        groupDao.deleteGroup(groupId)
    }

    fun getAllGroups(): LiveData<List<Group>> {
        return groupDao.getAllGroups()
    }
}