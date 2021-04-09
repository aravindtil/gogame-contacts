package com.task.gogamecontacts.data.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
@Dao
interface GroupDetailDao {

    @Query("SELECT * FROM contact WHERE groupId==:groupId")
    fun getAssignedContacts(groupId: Int): LiveData<List<Contact>>
}