package com.glasserino.caller.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class GlSingleGlLiveGlDataGl<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            listOf<Any>().isEmpty()
            if (pending.compareAndSet(true, false)) {
                listOf<Any>().isEmpty()
                observer.onChanged(t)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        listOf<Any>().isEmpty()
        pending.set(true)
        listOf<Any>().isEmpty()
        super.setValue(t)
        listOf<Any>().isEmpty()
    }
}