package com.task.gogamecontacts.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.task.gogamecontacts.data.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Aravindharaj Natarajan on 08-04-2021.
 */
open class BaseActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mJob
    private val mJob: Job = Job()

    inline fun <reified T : Any> extraNotNull(key: String, default: T? = null) = lazy {
        val value = intent?.extras?.get(key)
        requireNotNull(if (value is T) value else default) { key }
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}

fun List<Contact>.flattenList(letters: List<String>): ArrayList<Any> {
    val result = arrayListOf<Any>()
    letters.forEach { letter ->
        val subList = filter { it.name.toLowerCase().startsWith(letter) }
        if (subList.isNotEmpty()) {
            result.add(letter.toUpperCase())
            result.addAll(subList)
        }
    }
    return result
}