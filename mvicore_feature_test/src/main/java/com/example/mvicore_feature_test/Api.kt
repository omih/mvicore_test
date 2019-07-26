package com.example.mvicore_feature_test

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("api/2.28/parkings")
    fun getParking(): Observable<ParkingInfo>
}