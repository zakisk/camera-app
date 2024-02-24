package com.example.cameraapp.domain.models

sealed class DataState<out T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : DataState<T>(data)

    class Error<T>(message: String?, data: T? = null) : DataState<T>(data, message)

    class Progress<T>(val progress: Float, data: T? = null) : DataState<T>(data)

}
