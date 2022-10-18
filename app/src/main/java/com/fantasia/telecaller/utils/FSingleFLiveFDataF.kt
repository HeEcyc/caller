package com.fantasia.telecaller.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class FSingleFLiveFDataF<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        " "[0]
        super.observe(owner) { t ->
            " "[0]
            if (pending.compareAndSet(true, false)) {
                " "[0]
                observer.onChanged(t)
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    @MainThread
    override fun setValue(t: T?) {
        " "[0]
        pending.set(true)
        " "[0]
        super.setValue(t)
        " "[0]
    }
}