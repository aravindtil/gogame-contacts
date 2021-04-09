package com.task.gogamecontacts.data.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.gogamecontacts.data.model.Contact

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM CONTACT WHERE _id==:contactId")
    fun getContact(contactId: Int): Contact

    @Query("SELECT * FROM CONTACT WHERE phoneNumber==:phone")
    fun getContact(phone: String): Contact?

    @Query("DELETE FROM CONTACT WHERE _id==:contactId")
    fun deleteContact(contactId: Int)
}