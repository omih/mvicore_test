package com.example.mvicore_feature_test

import android.util.Log
import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable


class Feature : ActorReducerFeature<Feature.Wish, Feature.Effect, Feature.State, Nothing>(
    initialState = State(),
    actor = ActorImpl(),
    reducer = ReducerImpl()
) {
    sealed class Wish {
        object LoadData : Wish()
    }

    data class State(
        val isLoading: Boolean = false,
        val info: ParkingInfo = ParkingInfo(),
        val error: Throwable? = null
    ) : Serializable

    sealed class Effect {
        object StartedLoading : Effect()
        class Loaded(val info: ParkingInfo) : Effect()
        class ErrorLoading(val throwable: Throwable) : Effect()
    }

    class ActorImpl : Actor<State, Wish, Effect> {
        override fun invoke(state: State, action: Wish): Observable<out Effect> {
            return when (action) {
                Wish.LoadData -> api()
                    .getParking()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        Effect.Loaded(it) as Effect
                    }
                    .startWith(Observable.just(Effect.StartedLoading))
                    .onErrorReturn { Effect.ErrorLoading(it) as Effect }
            }
        }

        private fun api(): Api {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://parking.fitdev.ru/")
                .client(OkHttpClient.Builder()
                    .addInterceptor(getLoggingInterceptor())
                    .build())
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.networkGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(Api::class.java)
        }

        private fun getLoggingInterceptor(): LoggingInterceptor {
            return LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .request("Request")
                .response("Response")
                .build()
        }

    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.StartedLoading -> state.copy(isLoading = true)
                is Effect.Loaded -> {
                    state.copy(isLoading = false, info = effect.info)
                }
                is Effect.ErrorLoading -> state.copy(isLoading = false, error = effect.throwable)
            }
    }


}