package com.example.reduxvideosample.redux

import android.view.TextureView
import com.example.redux.Action

sealed class AppAction : Action {
    object ClickSurface : AppAction()
}

sealed class PlayerAction : Action {
    data class Init(val videoView: TextureView) : PlayerAction()
    object Release : PlayerAction()
    object Play : PlayerAction()
    object Pause : PlayerAction()
    object Mute : PlayerAction()
    object UnMute : PlayerAction()
}
