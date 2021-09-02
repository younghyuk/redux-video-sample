package com.example.redux

interface StoreSubscriber<State> {
    fun newState(state: State)
}
