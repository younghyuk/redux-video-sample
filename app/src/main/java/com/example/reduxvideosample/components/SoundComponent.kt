package com.example.reduxvideosample.components

import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.R
import com.example.reduxvideosample.redux.AppState
import com.example.reduxvideosample.redux.PlayerAction

class SoundComponent(
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
            toggleSound()
        }
    }

    private fun toggleSound() {
        if (store.state.isMuted) {
            store.dispatch(PlayerAction.UnMute)
        } else {
            store.dispatch(PlayerAction.Mute)
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
        if (state.isMuted) {
            uiView.background = ContextCompat.getDrawable(context, R.drawable.ic_volume_off)
        } else {
            uiView.background = ContextCompat.getDrawable(context, R.drawable.ic_volume_up)
        }
    }
}