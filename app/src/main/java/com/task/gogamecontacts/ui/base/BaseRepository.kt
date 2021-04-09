package com.task.gogamecontacts.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseRepository : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job
    private val job = Job()

    companion object {
        const val TAG = "BaseRepository"
    }

    fun onDispose() {
        job.cancel()
    }
}