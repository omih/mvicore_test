package com.example.mvicore_feature_test

import com.google.gson.annotations.SerializedName

data class Parking(
    @SerializedName("address")
    var address: Address = Address()
)