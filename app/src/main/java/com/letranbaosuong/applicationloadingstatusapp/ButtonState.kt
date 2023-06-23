package com.letranbaosuong.applicationloadingstatusapp

sealed class ButtonState {
    object Clicked : ButtonState()
    object Loading : ButtonState()
    object Completed : ButtonState()
}