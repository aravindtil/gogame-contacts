package com.task.gogamecontacts.data.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.gogamecontacts.data.model.Group

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGroup(group: Group)

    @Query("SELECT *, (SELECT COUNT(*) FROM contact WHERE groupId==`group`._id) AS count FROM `group` ORDER BY name ASC")
    fun getAllGroups(): LiveData<List<Group>>

    @Query("SELECT * FROM `group` WHERE _id==:groupId")
    fun getGroup(groupId: Int): Group

    @Query("SELECT * FROM `group` WHERE name==:name")
    fun getGroup(name: String): Group?

    @Query("DELETE FROM `group` WHERE _id==:groupId")
    fun deleteGroup(groupId: Int)

}