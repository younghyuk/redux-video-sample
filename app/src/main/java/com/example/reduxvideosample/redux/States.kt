package com.example.reduxvideosample.redux

data class AppState(
    var isPlayed: Boolean = false,
    var isMuted: Boolean = false,
    var isExpanded: Boolean = false
)