package com.task.gogamecontacts.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Aravindharaj Natarajan on 09-04-2021.
 */
open class BaseViewModel: ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job
    private val job = Job()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}