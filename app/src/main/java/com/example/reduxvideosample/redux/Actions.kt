package com.example.reduxvideosample.redux

import android.view.TextureView
import com.example.redux.Action

sealed class AppAction : Action {
    object Collapse : AppAction()
    object Expand : AppAction()
}

sealed class PlayerAction : Action {
    data class Init(val videoView: TextureView) : PlayerAction()
    object Release : PlayerAction()
    object Play : PlayerAction()
    object Pause : PlayerAction()
    object Mute : PlayerAction()
    object UnMute : PlayerAction()
}

sealed class PlayerCallbackAction : Action {
    data class VolumeChanged(val volume: Float) : PlayerAction()
}
