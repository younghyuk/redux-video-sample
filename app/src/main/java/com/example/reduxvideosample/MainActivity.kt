package com.example.reduxvideosample

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import com.example.redux.Store
import com.example.redux.StoreSubscriber
import com.example.reduxvideosample.components.OverlayComponent
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
    private lateinit var overlayComponent: OverlayComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        removeTitleAndStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies(this)

        this.rootView = findViewById(R.id.rootView)

        initComponents(rootView)
        subscribeAll()
        store.dispatch(PlayerAction.Init(surfaceComponent.videoView))
        store.dispatch(PlayerAction.Play)
    }

    override fun onPause() {
        super.onPause()
        store.dispatch(PlayerAction.Pause)
    }

    override fun onDestroy() {
        super.onDestroy()
        store.dispatch(PlayerAction.Release)
        unsubscribeAll()
    }

    private fun removeTitleAndStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private fun subscribeAll() {
        store.subscribe(this)
        surfaceComponent.subscribe()
        overlayComponent.subscribe()
    }

    private fun unsubscribeAll() {
        store.unsubscribe(this)
        surfaceComponent.unsubscribe()
        overlayComponent.unsubscribe()
    }

    private fun initComponents(container: ViewGroup) {
        surfaceComponent = SurfaceComponent(container, store)
        overlayComponent = OverlayComponent(container, store)
    }

    private fun layoutUiComponents(container: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)

        surfaceComponent.getContainerId().let {
            constraintSet.clear(it)
            constraintSet.connect(it, TOP, PARENT_ID, TOP)
            constraintSet.connect(it, START, PARENT_ID, START)
            constraintSet.connect(it, END, PARENT_ID, END)
            constraintSet.constrainWidth(it, MATCH_CONSTRAINT)
            constraintSet.constrainHeight(it, MATCH_CONSTRAINT)
            constraintSet.setDimensionRatio(it, "H, 16:9")
        }

        overlayComponent.getContainerId().let {
            constraintSet.connect(it, TOP, surfaceComponent.getContainerId(), TOP)
            constraintSet.connect(it, START, surfaceComponent.getContainerId(), START)
            constraintSet.connect(it, END, surfaceComponent.getContainerId(), END)
            constraintSet.connect(it, BOTTOM, surfaceComponent.getContainerId(), BOTTOM)
        }

        constraintSet.applyTo(container)
    }

    private fun layoutUiComponentsOnFullscreen(container: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)

        surfaceComponent.getContainerId().let {
            constraintSet.clear(it)
            constraintSet.connect(it, TOP, PARENT_ID, TOP)
            constraintSet.connect(it, START, PARENT_ID, START)
            constraintSet.connect(it, END, PARENT_ID, END)
            constraintSet.connect(it, BOTTOM, PARENT_ID, BOTTOM)
        }

        overlayComponent.getContainerId().let {
            constraintSet.connect(it, TOP, surfaceComponent.getContainerId(), TOP)
            constraintSet.connect(it, START, surfaceComponent.getContainerId(), START)
            constraintSet.connect(it, END, surfaceComponent.getContainerId(), END)
            constraintSet.connect(it, BOTTOM, surfaceComponent.getContainerId(), BOTTOM)
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
        if (state.isExpanded) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            layoutUiComponentsOnFullscreen(rootView)
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            layoutUiComponents(rootView)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}