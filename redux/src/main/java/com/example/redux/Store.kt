package com.example.redux

import java.util.concurrent.CopyOnWriteArrayList

class Store<State>(
    reducer: Reducer<State>,
    middlewareList: List<Middleware<State>>,
    initState: State
) {
    private val dispatchFunctions: MutableList<DispatchFunction> = arrayListOf()
    private var subscriptions: MutableList<StoreSubscriber<State>> = CopyOnWriteArrayList()

    var state: State = initState
        private set(value) {
            field = value
            subscriptions.forEach { subscriber -> subscriber.newState(field) }
        }

    init {
        dispatchFunctions.add(object :
            DispatchFunction {
            @Synchronized
            override fun dispatch(action: Action) {
                val newState = reducer.invoke(action, state)
                if (state != newState) {
                    state = newState
                }
            }
        })
        middlewareList.reversed().map { middleware ->
            val next = dispatchFunctions.first()
            dispatchFunctions.add(0, object :
                DispatchFunction {
                override fun dispatch(action: Action) {
                    middleware.invoke(this@Store, action, next)
                }
            })
        }
    }

    fun dispatch(action: Action) {
        dispatchFunctions.first().dispatch(action)
    }

    fun subscribe(subscriber: StoreSubscriber<State>) {
        subscriptions.add(subscriber)
        subscriber.newState(state)
    }

    fun unsubscribe(subscriber: StoreSubscriber<State>) {
        subscriptions.remove(subscriber)
    }
}
