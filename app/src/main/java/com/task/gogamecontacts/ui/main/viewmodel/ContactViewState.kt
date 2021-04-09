package com.task.gogamecontacts.ui.main.viewmodel

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
sealed class ContactViewState {
    object ValidationSuccess :
        ContactViewState()

    object ContactAdded :
        ContactViewState()

    object ContactEdited :
        ContactViewState()

    object ContactDeleted :
        ContactViewState()

    class Error1(val error: Int) :
        ContactViewState()
}