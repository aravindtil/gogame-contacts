package com.task.gogamecontacts.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.task.gogamecontacts.BR


/**
 * Created by Aravindharaj Natarajan on 08-04-2021.
 */
@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,
    var groupId: Int = -1,
    @Ignore
    var selected: Boolean = false
): BaseObservable() {
    @get:Bindable
    var name: String = ""
        set(name) {
            field = name
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var phoneNumber: String = ""
        set(name) {
            field = name
            notifyPropertyChanged(BR.phoneNumber)
        }

    @get:Bindable
    var profilePicture: String = ""
        set(name) {
            field = name
            notifyPropertyChanged(BR.profilePicture)
        }

    @get:Bindable
    var favourite: Boolean = false
        set(name) {
            field = name
            notifyPropertyChanged(BR.favourite)
        }
}
