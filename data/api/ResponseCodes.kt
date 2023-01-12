package com.mercadopago.mpos.fcu.data.api

enum class ResponseCodes(val code: Int) {
    RESPONSE_OK(200),
    AUTH_ERROR(401),
    SERVER_ERROR(500)
}
