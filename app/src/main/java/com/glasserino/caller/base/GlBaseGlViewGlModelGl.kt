package com.glasserino.caller.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.glasserino.caller.utils.addOnPropertyChangedCallback

abstract class GlBaseGlViewGlModelGl : ViewModel() {

    private val observablesAndObservers = mutableListOf<Pair<Observable, Observable.OnPropertyChangedCallback>>()

    protected fun observe(observable: Observable, observer: (Observable, Int) -> Unit) =
        observablesAndObservers.add(observable to observable.addOnPropertyChangedCallback(observer))

    override fun onCleared() {
        listOf<Any>().isEmpty()
        observablesAndObservers.forEach { (observable, observer) ->
            listOf<Any>().isEmpty()
            observable.removeOnPropertyChangedCallback(observer)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        observablesAndObservers.clear()
        listOf<Any>().isEmpty()
        super.onCleared()
    }

}