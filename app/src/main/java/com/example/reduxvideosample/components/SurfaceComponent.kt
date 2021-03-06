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
) : UiComponent, StoreSubscriber<AppState> {

    private val uiView = TextureView(container.context).apply {
        id = ViewCompat.generateViewId()
    }

    init {
        container.addView(uiView)
    }

    val videoView: TextureView
        get() = uiView

    override fun getContainerId(): Int = uiView.id

    override fun subscribe() {
        store.subscribe(this)
    }

    override fun unsubscribe() {
        store.unsubscribe(this)
    }

    override fun newState(state: AppState) {
    }
}