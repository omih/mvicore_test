package com.example.mvicore_feature_test

import com.google.gson.annotations.SerializedName


class ParkingInfo(
    @SerializedName("parkings")
    var parkings: List<Parking> = listOf()
)