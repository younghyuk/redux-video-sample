package com.example.reduxvideosample.middlewares

import com.example.redux.Action
import com.example.redux.DispatchFunction
import com.example.redux.Middleware
import com.example.redux.Store
import com.example.reduxvideosample.redux.AppState
import com.example.reduxvideosample.redux.PlayerAction
import com.example.reduxvideosample.redux.PlayerCallbackAction
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayerMiddleware(
    private val player: SimpleExoPlayer
) : Middleware<AppState> {

    override fun invoke(store: Store<AppState>, action: Action, next: DispatchFunction) {
        when (action) {
            is PlayerAction.Init -> {
                player.addListener(object : Player.Listener {
                    override fun onVolumeChanged(volume: Float) {
                        store.dispatch(PlayerCallbackAction.VolumeChanged(volume))
                    }
                })
                player.setVideoTextureView(action.videoView)
                val mediaItem = MediaItem.fromUri(SAMPLE_VIDEO_URI)
                player.setMediaItem(mediaItem)
                player.prepare()
            }
            is PlayerAction.Release -> {
                player.release()
            }
            is PlayerAction.Play -> player.play()
            is PlayerAction.Pause -> player.pause()
            is PlayerAction.Mute -> player.volume = 0F
            is PlayerAction.UnMute -> player.volume = 1F
        }
        next.dispatch(action)
    }

    companion object {
        const val SAMPLE_VIDEO_URI =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    }
}