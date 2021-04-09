package com.task.gogamecontacts.ui.main.viewmodel

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
sealed class GroupViewState {
    object ValidationSuccess :
        GroupViewState()

    object GroupAdded :
        GroupViewState()

    object GroupEdited :
        GroupViewState()

    object GroupDeleted :
        GroupViewState()

    class Error1(val error: Int) :
        GroupViewState()
}