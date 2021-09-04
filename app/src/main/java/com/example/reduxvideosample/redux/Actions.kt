package com.example.reduxvideosample.redux

import com.example.redux.Action

sealed class AppAction : Action {
    object ClickSurface : AppAction()
}

