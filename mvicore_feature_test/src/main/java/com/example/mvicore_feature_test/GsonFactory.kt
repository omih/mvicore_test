package com.example.mvicore_feature_test

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {
    val networkGson: Gson by lazy {
        val gsonBuilder = GsonBuilder()
        //general
        gsonBuilder.registerTypeAdapter(List::class.java, ListDeserializer())

        //project-related

        gsonBuilder.create()
    }
}
