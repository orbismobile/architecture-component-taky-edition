package com.elcomercio.mvvm_dagger_kotlin.utils

import android.arch.lifecycle.LiveData

/**
 * @author carlosleonardocamilovargashuaman on 4/24/18.
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
