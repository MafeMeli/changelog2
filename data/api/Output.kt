package com.mercadopago.mpos.fcu.data.api

import java.io.IOException
import java.util.concurrent.TimeoutException

sealed class Output<out T> {
    data class Success<out T>(val data: T) : Output<T>()
    data class Error(val failure: Exception) : Output<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[failure=$failure]"
        }
    }

    fun isNetworkError(): Boolean {
        return this is Error && (failure is IOException || failure is TimeoutException)
    }
}