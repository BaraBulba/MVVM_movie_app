package com.andrew.solokhov.mvvmmovieapp.presentation.utils

import android.text.style.ClickableSpan
import android.view.View

class NewClickableSpan(
    private val onSpanTextClick: () -> Unit
): ClickableSpan() {

    override fun onClick(widget: View) {
        onSpanTextClick.invoke()
    }
}