package com.example.reduxvideosample.components

import android.view.LayoutInflater
import android.view.TextureView
import android.view.ViewGroup
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.R
import com.example.reduxvideosample.redux.AppState

class SurfaceComponent(
    container: ViewGroup,
    private val store: Store<AppState>
) : StoreSubscriber<AppState> {

    private val uiView =
        LayoutInflater.from(container.context)
            .inflate(R.layout.surface, container, true)

    val videoView: TextureView
        get() = uiView.findViewById(R.id.textureView)

    fun getContainerId(): Int = uiView.id

    fun init() {
        store.subscribe(this)
    }

    fun release() {
        store.unsubscribe(this)
    }

    override fun newState(state: AppState) {
    }
}