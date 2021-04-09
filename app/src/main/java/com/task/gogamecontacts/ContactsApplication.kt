package com.task.gogamecontacts

import android.app.Application

/**
 * Created by Aravindharaj Natarajan on 08-04-2021.
 */
class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getInstance(): ContactsApplication? {
        return instance
    }

    companion object {
        lateinit var instance: ContactsApplication
            private set
    }
}