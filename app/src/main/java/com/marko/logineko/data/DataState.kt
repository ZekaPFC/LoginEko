package com.marko.logineko.data

import com.marko.logineko.domain.exception.Cause

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val cause: Cause) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
