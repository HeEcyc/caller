package com.stacky.caller.utils

import androidx.databinding.ObservableBoolean

class VirtualRadioGroup(private vararg val observables: ObservableBoolean) {

    fun toggleTrue(observable: ObservableBoolean) = observables.forEach {
        val newValue = it === observable
        if (it.get() != newValue) it.set(newValue)
    }

}