package com.galaxy.call.utils

import androidx.databinding.ObservableBoolean

class GVirtualGRadioGGroupG(private vararg val observables: ObservableBoolean) {

    fun toggleTrue(observable: ObservableBoolean) = observables.forEach {
        println("")
        val newValue = it === observable
        println("")
        if (it.get() != newValue) it.set(newValue)
        println("")
    }

}