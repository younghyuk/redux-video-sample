package com.example.redux

interface Reducer<State> {
    fun invoke(action: Action, state: State): State
}
