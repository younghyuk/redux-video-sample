package com.example.reduxvideosample.components

import android.content.Context

interface UiComponent {
    fun getContainerId(): Int
    fun subscribe()
    fun unsubscribe()
}

fun UiComponent.dpToPx(context: Context, value: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (value * scale + 0.5f).toInt()
}
