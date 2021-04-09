package com.task.gogamecontacts.data.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
@Dao
interface GroupAssignDao {

    @Query("SELECT * FROM contact WHERE favourite==1 AND (groupId==-1 OR groupId==:groupId)")
    fun getAssignableContacts(groupId: Int): LiveData<List<Contact>>

    @Transaction
    @Update
    fun assignContacts(data: List<Contact>)
}