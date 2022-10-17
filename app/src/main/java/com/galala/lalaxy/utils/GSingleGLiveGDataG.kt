package com.galala.lalaxy.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class GSingleGLiveGDataG<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        println("")
        super.observe(owner) { t ->
            println("")
            if (pending.compareAndSet(true, false)) {
                println("")
                observer.onChanged(t)
                println("")
            }
            println("")
        }
        println("")
    }

    @MainThread
    override fun setValue(t: T?) {
        println("")
        pending.set(true)
        println("")
        super.setValue(t)
        println("")
    }
}