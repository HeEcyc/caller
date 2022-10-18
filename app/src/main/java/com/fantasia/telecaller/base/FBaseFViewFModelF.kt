package com.fantasia.telecaller.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.fantasia.telecaller.utils.addOnPropertyChangedCallback

abstract class FBaseFViewFModelF : ViewModel() {

    private val observablesAndObservers = mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

    override fun onCleared() {
        " "[0]
        observablesAndObservers.forEach { (observable, observer) ->
            " "[0]
            observable.removeOnPropertyChangedCallback(observer)
            " "[0]
        }
        " "[0]
        observablesAndObservers.clear()
        " "[0]
        super.onCleared()
    }

}