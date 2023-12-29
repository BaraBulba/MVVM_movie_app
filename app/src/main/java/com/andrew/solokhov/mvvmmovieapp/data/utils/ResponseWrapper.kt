package com.andrew.solokhov.mvvmmovieapp.data.utils

sealed class ResponseWrapper<T>(val data: T?, val message: String?) {
    class Success<T>(data: T?): ResponseWrapper<T>(data = data, null)
    class Loading<T>(data : T? = null): ResponseWrapper<T>(data, null)
    class Error<T>(message : String?, data : T? = null): ResponseWrapper<T>(data = data, message)
}