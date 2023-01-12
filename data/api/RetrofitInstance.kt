package com.mercadopago.mpos.fcu.data.api

import com.mercadopago.mpos.fcu.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val gitApi: GitHubAPI by lazy {
        retrofit.create(GitHubAPI::class.java)
    }
}
