package com.example.reduxvideosample.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.R
import com.example.reduxvideosample.redux.AppState

class OverlayComponent(
    container: ViewGroup,
    private val store: Store<AppState>
) : UiComponent, StoreSubscriber<AppState> {

    private val uiView = LayoutInflater.from(container.context)
        .inflate(R.layout.component_overlay, container, false) as ConstraintLayout

    init {
        container.addView(uiView)
        initComponents(uiView)
        layoutUiComponents(uiView)
    }

    override fun getContainerId(): Int = uiView.id

    override fun subscribe() {
        store.subscribe(this)
    }

    override fun unsubscribe() {
        store.unsubscribe(this)
    }

    private fun initComponents(container: ViewGroup) {
    }

    private fun layoutUiComponents(container: ConstraintLayout) {
    }

    override fun newState(state: AppState) {}
}