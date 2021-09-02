package com.example.redux

interface Middleware<State> {
    fun invoke(store: Store<State>, action: Action, next: DispatchFunction)
}
