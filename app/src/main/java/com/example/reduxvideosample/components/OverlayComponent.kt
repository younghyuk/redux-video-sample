package com.example.reduxvideosample.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.R
import com.example.reduxvideosample.redux.AppState

class OverlayComponent(
    container: ViewGroup,
    private val store: Store<AppState>
) : UiComponent, StoreSubscriber<AppState> {

    private val context = container.context

    private val uiView = LayoutInflater.from(context)
        .inflate(R.layout.component_overlay, container, false) as ConstraintLayout

    private lateinit var soundComponent: SoundComponent

    init {
        container.addView(uiView)
        initComponents(uiView)
        layoutUiComponents(uiView)
    }

    override fun getContainerId(): Int = uiView.id

    override fun subscribe() {
        store.subscribe(this)
        soundComponent.subscribe()
    }

    override fun unsubscribe() {
        store.unsubscribe(this)
        soundComponent.unsubscribe()
    }

    private fun initComponents(container: ViewGroup) {
        soundComponent = SoundComponent(container, store)
    }

    private fun layoutUiComponents(container: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)

        constraintSet.connect(soundComponent.getContainerId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(context, 10))
        constraintSet.connect(soundComponent.getContainerId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dpToPx(context, 10))

        constraintSet.applyTo(container)
    }

    override fun newState(state: AppState) {}
}
