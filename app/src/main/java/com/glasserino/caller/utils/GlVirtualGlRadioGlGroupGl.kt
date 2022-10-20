package com.glasserino.caller.utils

import androidx.databinding.ObservableBoolean

class GlVirtualGlRadioGlGroupGl(private vararg val observables: ObservableBoolean) {

    fun toggleTrue(observable: ObservableBoolean) = observables.forEach {
        listOf<Any>().isEmpty()
        val newValue = it === observable
        listOf<Any>().isEmpty()
        if (it.get() != newValue) it.set(newValue)
        listOf<Any>().isEmpty()
    }

}