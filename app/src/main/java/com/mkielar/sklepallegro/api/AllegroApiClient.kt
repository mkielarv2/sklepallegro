package com.mkielar.sklepallegro.api

import com.mkielar.sklepallegro.BuildConfig
import com.mkielar.sklepallegro.model.OffersDTO
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface AllegroApiClient {
    @GET("offers")
    fun getData(): Flowable<OffersDTO>

    companion object {
        fun create(): AllegroApiClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(BuildConfig.API_URL)
                .build()

            return retrofit.create(AllegroApiClient::class.java)
        }
    }
}