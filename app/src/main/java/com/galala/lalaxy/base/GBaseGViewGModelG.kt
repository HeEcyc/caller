package com.galala.lalaxy.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.galala.lalaxy.utils.addOnPropertyChangedCallback

abstract class GBaseGViewGModelG : ViewModel() {

    private val observablesAndObservers = mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

    override fun onCleared() {
        println("")
        observablesAndObservers.forEach { (observable, observer) ->
            println("")
            observable.removeOnPropertyChangedCallback(observer)
            println("")
        }
        println("")
        observablesAndObservers.clear()
        println("")
        super.onCleared()
        println("")
    }

}