package com.example.mvicore_feature_test

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("street")
    var street: Street = Street(),
    @SerializedName("house")
    var house: House = House()
)