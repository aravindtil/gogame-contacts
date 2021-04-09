package com.task.gogamecontacts.utils.ktxextensions

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

class OnSingleClickListener(
        private val clickListener: View.OnClickListener,
        private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            clickListener.onClick(v)
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
            }?:run{
                canClick.set(true)
            }
        }
    }
}