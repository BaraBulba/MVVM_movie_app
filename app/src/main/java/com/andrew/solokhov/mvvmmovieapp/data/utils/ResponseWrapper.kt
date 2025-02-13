package com.andrew.solokhov.mvvmmovieapp.data.utils

sealed class ResponseWrapper<T>(val isLoading: Boolean?, val data: T?, val message: String?) {
    class Success<T>(data: T?) : ResponseWrapper<T>(isLoading = null, data = data, null)
    class Loading<T>(data: T? = null) : ResponseWrapper<T>(isLoading = true, data, null)
    class Error<T>(message: String?, data: T? = null) :
        ResponseWrapper<T>(isLoading = null, data = data, message)
}