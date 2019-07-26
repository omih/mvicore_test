package com.example.mvicore_test

data class ViewModel(
    val isLoading: Boolean,
    val info: List<InfoItem>,
    val throwable: Throwable?
)