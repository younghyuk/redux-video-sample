package com.example.reduxvideosample.components

import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.R
import com.example.reduxvideosample.redux.AppAction
import com.example.reduxvideosample.redux.AppState
import com.example.reduxvideosample.redux.PlayerAction

class FullscreenComponent(
    container: ViewGroup,
    private val store: Store<AppState>
) : UiComponent, StoreSubscriber<AppState> {

    private val context = container.context

    private val uiView = ImageButton(context).apply {
        id = ViewCompat.generateViewId()
    }

    init {
        container.addView(uiView)

        uiView.setOnClickListener {
            toggleFullscreen()
        }
    }

    private fun toggleFullscreen() {
        if (store.state.isExpanded) {
            store.dispatch(AppAction.Collapse)
        } else {
            store.dispatch(AppAction.Expand)
        }
    }

    override fun getContainerId(): Int = uiView.id

    override fun subscribe() {
        store.subscribe(this)
    }

    override fun unsubscribe() {
        store.unsubscribe(this)
    }

    override fun newState(state: AppState) {
        if (state.isExpanded) {
            uiView.background = ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_exit)
        } else {
            uiView.background = ContextCompat.getDrawable(context, R.drawable.ic_fullscreen)
        }
    }
}