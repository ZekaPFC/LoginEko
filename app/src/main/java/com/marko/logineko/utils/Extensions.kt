package com.marko.logineko.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marko.logineko.data.DataState
import com.marko.logineko.data.network.ErrorResponse
import com.marko.logineko.data.network.NetworkExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException

fun <T> executeRequest(block: suspend () -> T): Flow<DataState<T>> {
    return flow {
        try {
            emit(DataState.Loading)
            emit(DataState.Success(block.invoke()))
        } catch (e: Exception) {
            emit(DataState.Error(NetworkExceptionHandler.handleError(e)))
        }
    }.flowOn(Dispatchers.IO)
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun HttpException.parseNetworkResponse(): String {
    val gson = Gson()
    val type = object : TypeToken<ErrorResponse>() {}.type
    val errorResponse: ErrorResponse? =
        gson.fromJson(this.response()?.errorBody()?.charStream(), type)
    return errorResponse?.message ?: ""
}

fun Fragment.launch(block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block.invoke()
        }
    }
}

fun LatLngBounds.Builder.includeAll(points: List<LatLng>): LatLngBounds.Builder {
    points.forEach { this.include(it) }
    return this
}
