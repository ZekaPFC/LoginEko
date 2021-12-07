package com.marko.logineko.data.network

import com.marko.logineko.domain.exception.Cause
import com.marko.logineko.utils.parseNetworkResponse
import retrofit2.HttpException
import java.io.IOException

object NetworkExceptionHandler {
    fun handleError(throwable: Exception): Cause {
        return when (throwable) {
            is IOException -> Cause.NetworkException
            is HttpException -> Cause.RequestError(throwable.parseNetworkResponse())
            else -> Cause.GenericError
        }
    }
}
