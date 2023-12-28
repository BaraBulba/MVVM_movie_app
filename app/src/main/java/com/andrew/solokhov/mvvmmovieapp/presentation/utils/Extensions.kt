package com.andrew.solokhov.mvvmmovieapp.presentation.utils

import android.content.Context
import android.widget.Toast

fun showToastMessage(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}