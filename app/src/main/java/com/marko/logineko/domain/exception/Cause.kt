package com.marko.logineko.domain.exception

sealed class Cause {
    object NetworkException : Cause()
    object GenericError : Cause()
    data class RequestError(val message: String) : Cause()
}
