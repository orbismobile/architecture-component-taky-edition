package com.elcomercio.mvvm_dagger_kotlin.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import com.elcomercio.mvvm_dagger_kotlin.utils.VIEW_MODEL_NOT_FOUND

/**
 * Created by tohure on 20/12/17.
 */

@SuppressWarnings("unchecked")
@Singleton
class SampleViewModelFactory
@Inject
internal constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            throw IllegalArgumentException("$VIEW_MODEL_NOT_FOUND - $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}