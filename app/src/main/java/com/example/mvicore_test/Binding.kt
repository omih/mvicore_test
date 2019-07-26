package com.example.mvicore_test

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import com.example.mvicore_feature_test.Feature

class Binding(
    view: MainActivity,
    private val feature: Feature
) : AndroidBindings<MainActivity>(view) {

    override fun setup(view: MainActivity) {
        binder.bind(feature to view using Mapper())
        binder.bind(view to feature using Mapper2())
    }
}

class Mapper : (Feature.State) -> ViewModel {
    override fun invoke(p1: Feature.State): ViewModel {
        return ViewModel(
            p1.isLoading,
            p1.info.parkings.map { InfoItem("${it.address.street.ru} ${it.address.house.ru}") },
            p1.error
        )
    }
}

class Mapper2 : (UiEvent) -> Feature.Wish {
    override fun invoke(p1: UiEvent): Feature.Wish {
        return when(p1) {
            UiEvent.GetInfo -> Feature.Wish.LoadData
        }
    }

}