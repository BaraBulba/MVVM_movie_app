package com.andrew.solokhov.mvvmmovieapp.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun showToastMessage(message: String, context: Context) {
    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
}

context (View)
fun Fragment.removeKeyboard() {
    val manager =
        this.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, 0)
}