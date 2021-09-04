package com.example.reduxvideosample.components

import android.view.TextureView
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.redux.AppState

class SurfaceComponent(
    container: ViewGroup,
    private val store: Store<AppState>
) : StoreSubscriber<AppState> {

    private val uiView = TextureView(container.context).apply {
        id = ViewCompat.generateViewId()
    }

    init {
        container.addView(uiView)
    }

    val videoView: TextureView
        get() = uiView

    fun getContainerId(): Int = uiView.id

    fun subscribe() {
        store.subscribe(this)
    }

    fun unsubscribe() {
        store.unsubscribe(this)
    }

    override fun newState(state: AppState) {
    }
}