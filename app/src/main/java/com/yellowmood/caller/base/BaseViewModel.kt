package com.yellowmood.caller.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.yellowmood.caller.utils.addOnPropertyChangedCallback

abstract class BaseViewModel : ViewModel() {

    private val observablesAndObservers = mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

    override fun onCleared() {
        observablesAndObservers.forEach { (observable, observer) ->
            observable.removeOnPropertyChangedCallback(observer)
        }
        observablesAndObservers.clear()
        super.onCleared()
    }

}