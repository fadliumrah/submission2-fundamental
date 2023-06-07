package com.fadli.submission2.util

import android.view.View

interface ViewStateCallback<T> {

    val invisible: Int
    get() = View.INVISIBLE

    val visible: Int
    get() = View.VISIBLE

    fun onSuccess(data: T)
    fun onLoading()
    fun onFailed(message: String?)
}