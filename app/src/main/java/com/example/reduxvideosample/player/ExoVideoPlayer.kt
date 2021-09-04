package com.example.reduxvideosample.player

import android.content.Context
import android.view.TextureView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class ExoVideoPlayer(context: Context) {
    private val player: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()

    fun setVideoView(textureView: TextureView?) {
        player.setVideoTextureView(textureView)
    }

    fun loadVideo() {
        val mediaItem = MediaItem.fromUri(SAMPLE_VIDEO_URI)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun release() {
        player.release()
    }

    companion object {
        const val SAMPLE_VIDEO_URI =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    }
}