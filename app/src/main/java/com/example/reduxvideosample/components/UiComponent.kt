package com.example.reduxvideosample.components

interface UiComponent {
    fun getContainerId(): Int
    fun subscribe()
    fun unsubscribe()
}
