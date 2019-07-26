package com.example.mvicore_test

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import androidx.core.view.ViewCompat
import androidx.annotation.RequiresApi
import android.view.KeyEvent
import android.view.View


abstract class ObservableSourceActivity<T> : AppCompatActivity(), ObservableSource<T> {

    private val source = PublishSubject.create<T>()

    protected fun onNext(t: T) {
        source.onNext(t)
    }

    override fun subscribe(observer: Observer<in T>) {
        source.subscribe(observer)
    }

}
