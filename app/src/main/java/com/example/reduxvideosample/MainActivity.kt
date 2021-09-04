package com.example.reduxvideosample

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.redux.Store
import com.example.reduxvideosample.components.SurfaceComponent
import com.example.reduxvideosample.player.ExoVideoPlayer
import com.example.reduxvideosample.redux.AppReducer
import com.example.reduxvideosample.redux.AppState

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoVideoPlayer
    private lateinit var store: Store<AppState>
    private lateinit var rootView: ConstraintLayout

    private lateinit var surfaceComponent: SurfaceComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies(this)

        this.rootView = findViewById(R.id.rootView)

        initComponents(rootView)
        layoutUiComponents(rootView)
        initPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        surfaceComponent.release()
        releasePlayer()
    }

    private fun initPlayer() {
        player.setVideoView(surfaceComponent.videoView)
        player.loadVideo()
        player.play()
    }

    private fun releasePlayer() {
        player.setVideoView(null)
        player.release()
    }

    private fun initComponents(container: ViewGroup) {
        surfaceComponent = SurfaceComponent(container, store).also { it.init() }
    }

    private fun layoutUiComponents(rootViewContainer: ConstraintLayout) {
        val rootConstraintSet = ConstraintSet()
        rootConstraintSet.clone(rootViewContainer)

        surfaceComponent.getContainerId().let {
            rootConstraintSet.connect(
                it,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            rootConstraintSet.connect(
                it,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            rootConstraintSet.connect(
                it,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            rootConstraintSet.connect(
                it,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
        }

        rootConstraintSet.applyTo(rootViewContainer)
    }


    private fun injectDependencies(context: Context) {
        this.player = ExoVideoPlayer(context)
        this.store = Store(
            AppReducer(),
            listOf(),
            AppState()
        )
    }

    companion object {
        val FREE_VIDEO_URI =
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    }
}