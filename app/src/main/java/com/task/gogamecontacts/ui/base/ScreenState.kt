package com.task.gogamecontacts.ui.base

sealed class ScreenState<out T> {
    object ListEmpty : ScreenState<Nothing>()
    object Loading : ScreenState<Nothing>()
    class ShowErrorMessage(val message: String) : ScreenState<Nothing>()
    class ShowErrorMessage1(val message: Int) : ScreenState<Nothing>()
    class Render<T>(val renderState: T) : ScreenState<T>()
}