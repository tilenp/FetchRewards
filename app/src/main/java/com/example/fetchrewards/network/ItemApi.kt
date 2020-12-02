package com.example.fetchrewards.network

import io.reactivex.Single
import retrofit2.http.GET

interface ItemApi {

    @GET("/hiring.json")
    fun getItems(): Single<List<RemoteItem>>
}