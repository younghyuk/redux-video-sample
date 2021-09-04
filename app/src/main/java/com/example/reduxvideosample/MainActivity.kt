package com.example.reduxvideosample

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.components.SurfaceComponent
import com.example.reduxvideosample.middlewares.PlayerMiddleware
import com.example.reduxvideosample.redux.AppReducer
import com.example.reduxvideosample.redux.AppState
import com.example.reduxvideosample.redux.PlayerAction
import com.google.android.exoplayer2.SimpleExoPlayer

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    private lateinit var player: SimpleExoPlayer
    private lateinit var store: Store<AppState>
    private lateinit var rootView: ConstraintLayout

    private lateinit var surfaceComponent: SurfaceComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies(this)

        this.rootView = findViewById(R.id.rootView)

        initComponents(rootView)
        subscribeAll()
        store.dispatch(PlayerAction.Init(surfaceComponent.videoView))
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(PlayerAction.Play)
    }

    override fun onStop() {
        super.onStop()
        store.dispatch(PlayerAction.Pause)
    }

    private fun subscribeAll() {
        store.subscribe(this)
        surfaceComponent.subscribe()
    }

    private fun unsubscribeAll() {
        store.unsubscribe(this)
        surfaceComponent.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        store.dispatch(PlayerAction.Release)
        unsubscribeAll()
    }

    private fun initComponents(container: ViewGroup) {
        surfaceComponent = SurfaceComponent(container, store)
    }

    private fun layoutUiComponents(container: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)

        surfaceComponent.getContainerId().let {
            constraintSet.clear(it)
            constraintSet.connect(it, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            constraintSet.connect(it, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(it, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            constraintSet.constrainWidth(it, ConstraintSet.MATCH_CONSTRAINT)
            constraintSet.constrainHeight(it, ConstraintSet.MATCH_CONSTRAINT)
            constraintSet.setDimensionRatio(it, "H, 16:9")
        }

        constraintSet.applyTo(container)
    }

    private fun injectDependencies(context: Context) {
        this.player = SimpleExoPlayer.Builder(context).build()
        this.store = Store(
            AppReducer(),
            listOf(PlayerMiddleware(player)),
            AppState()
        )
    }

    override fun newState(state: AppState) {
        Log.d(TAG, "newState is called")
        layoutUiComponents(rootView)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}