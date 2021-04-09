package com.task.gogamecontacts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.gogamecontacts.data.repository.dao.ContactDao
import com.task.gogamecontacts.data.repository.dao.GroupAssignDao
import com.task.gogamecontacts.data.repository.dao.GroupDao
import com.task.gogamecontacts.data.repository.dao.GroupDetailDao
import com.task.gogamecontacts.data.model.Contact
import com.task.gogamecontacts.data.model.Group

@Database(
    entities = [
        Contact::class,
        Group::class
    ], version = 1
)

abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    abstract fun groupDao(): GroupDao

    abstract fun groupDetailDao(): GroupDetailDao

    abstract fun groupAssignDao(): GroupAssignDao

    companion object {

        @Volatile
        private var instance: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ContactsDatabase {
            return Room.databaseBuilder(context, ContactsDatabase::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}