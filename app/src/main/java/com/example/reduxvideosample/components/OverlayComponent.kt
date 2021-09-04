package com.example.reduxvideosample.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
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
    private lateinit var fullscreenComponent: FullscreenComponent

    init {
        container.addView(uiView)
        initComponents(uiView)
        layoutUiComponents(uiView)
    }

    override fun getContainerId(): Int = uiView.id

    override fun subscribe() {
        store.subscribe(this)
        soundComponent.subscribe()
        fullscreenComponent.subscribe()
    }

    override fun unsubscribe() {
        store.unsubscribe(this)
        soundComponent.unsubscribe()
        fullscreenComponent.unsubscribe()
    }

    private fun initComponents(container: ViewGroup) {
        soundComponent = SoundComponent(container, store)
        fullscreenComponent = FullscreenComponent(container, store)
    }

    private fun layoutUiComponents(container: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)

        soundComponent.getContainerId().let {
            constraintSet.connect(it, START, PARENT_ID, START, dpToPx(context, 10))
            constraintSet.connect(it, BOTTOM, PARENT_ID, BOTTOM, dpToPx(context, 10))
        }

        fullscreenComponent.getContainerId().let {
            constraintSet.connect(it, END, PARENT_ID, END, dpToPx(context, 10))
            constraintSet.connect(it, BOTTOM, PARENT_ID, BOTTOM, dpToPx(context, 10))
        }

        constraintSet.applyTo(container)
    }

    override fun newState(state: AppState) {}
}
