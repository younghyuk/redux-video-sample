package com.example.reduxvideosample.redux

import android.util.Log
import com.example.redux.Action
import com.example.redux.Reducer

class AppReducer : Reducer<AppState> {
    override fun invoke(action: Action, state: AppState): AppState {
        Log.d(TAG, "invoke: $action")
        var newState = state
        when (action) {
            is PlayerCallbackAction.VolumeChanged -> {
                newState = newState.copy(
                    isMuted = action.volume == 0F
                )
            }
            is AppAction.Expand, AppAction.Collapse -> {
                newState = newState.copy(
                    isExpanded = action is AppAction.Expand
                )
            }
        }

        return newState
    }

    companion object {
        const val TAG = "AppReducer"
    }
}