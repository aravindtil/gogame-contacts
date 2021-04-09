package com.task.gogamecontacts.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.gogamecontacts.R
import com.task.gogamecontacts.ui.base.BaseViewModel
import com.task.gogamecontacts.ui.base.ScreenState
import com.task.gogamecontacts.data.model.Group
import com.task.gogamecontacts.data.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
open class GroupViewModel internal constructor() : BaseViewModel() {

    private val _groupViewState: MutableLiveData<ScreenState<GroupViewState>> =
        MutableLiveData()
    val groupViewState: LiveData<ScreenState<GroupViewState>>
        get() = _groupViewState

    var group: MutableLiveData<Group> = MutableLiveData(Group())
    var groups: LiveData<List<Group>> = GroupRepository.getAllGroups()

    fun saveGroup(groupId: Int) {
        launch {
            async(Dispatchers.IO) {
                group.value?.let { GroupRepository.saveGroup(it) }
            }.await().let {
                _groupViewState.postValue(
                    ScreenState.Render(
                        if (groupId == -1) GroupViewState.GroupAdded
                        else GroupViewState.GroupEdited
                    )
                )
            }
        }
    }

    fun onSaveClick(groupId: Int) {
        val group = group.`value`
        when {
            group?.name.isNullOrEmpty() -> {
                _groupViewState.value =
                    ScreenState.Render(
                        GroupViewState.Error1(
                            R.string.name_error
                        )
                    )
                return
            }
            else -> {
                if (groupId == -1)
                    launch {
                        async(Dispatchers.IO) {
                            return@async group?.name?.let { fetchGroup(it) }
                        }.await().let {
                            if (it == null) {
                                _groupViewState.postValue(
                                    ScreenState.Render(
                                        GroupViewState.ValidationSuccess
                                    )
                                )
                            } else {
                                _groupViewState.postValue(
                                    ScreenState.Render(
                                        GroupViewState.Error1(
                                            R.string.dup_group
                                        )
                                    )
                                )
                            }
                        }
                    }
                else
                    _groupViewState.value =
                        ScreenState.Render(
                            GroupViewState.ValidationSuccess
                        )
            }
        }
    }

    fun fetchGroup(groupId: Int) {
        launch {
            async(Dispatchers.IO) {
                return@async GroupRepository.getGroup(groupId)
            }.await().let {
                group.postValue(it)
            }
        }
    }

    fun fetchGroup(name: String): Group? {
        return GroupRepository.getGroup(name)
    }

    fun onDelete(groupId: Int) {
        launch {
            async(Dispatchers.IO) {
                group.value?.let { GroupRepository.deleteGroup(groupId) }
            }.await().let {
                _groupViewState.postValue(
                    ScreenState.Render(
                        GroupViewState.GroupDeleted
                    )
                )
            }
        }
    }

    class GroupViewModelFactory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GroupViewModel() as T
        }
    }
}