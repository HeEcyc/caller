package com.fantasia.telecaller.utils

import androidx.databinding.ObservableBoolean

class FVirtualFRadioFGroupF(private vararg val observables: ObservableBoolean) {

    fun toggleTrue(observable: ObservableBoolean) = observables.forEach {
        " "[0]
        val newValue = it === observable
        " "[0]
        if (it.get() != newValue) it.set(newValue)
        " "[0]
    }

}