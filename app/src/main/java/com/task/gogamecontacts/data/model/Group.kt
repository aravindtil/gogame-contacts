package com.task.gogamecontacts.data.model

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.gogamecontacts.BR

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
@Entity(tableName = "group")
data class Group(
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,
    var count: Int = 0
) : BaseObservable() {
    var name: String = ""
        set(name) {
            field = name
            notifyPropertyChanged(BR.name)
        }
}