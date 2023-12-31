package com.andrew.solokhov.mvvmmovieapp.presentation.navigation

import androidx.activity.OnBackPressedCallback

class NewOnBackPress (
    private val newOnBackPressCallback: NewOnBackPressCallback
): OnBackPressedCallback(true) {

    override fun handleOnBackPressed() {
        newOnBackPressCallback.newOnBackPressCallback()
    }

    interface NewOnBackPressCallback{
        fun newOnBackPressCallback()
    }
}