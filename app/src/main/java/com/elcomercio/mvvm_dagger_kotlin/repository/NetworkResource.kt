package com.elcomercio.mvvm_dagger_kotlin.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.util.Log
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.elcomercio.mvvm_dagger_kotlin.utils.AppExecutors
import com.elcomercio.mvvm_dagger_kotlin.utils.ERROR_SERVICE_RESPONSE
import com.elcomercio.mvvm_dagger_kotlin.utils.Resource


/**
 * This Helper class can be reused in multiple places.
 * Because loading data from network while showing it from the disk is a common use case.
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For information about how DetailNetworkResource class works, read the official documentation:
 * <a href="https://developer.android.com/topic/libraries/architecture/guide.html#addendum">
 * Addendum: exposing network status.</a>
 * </p>
 * </div>
 * This class fetch the result from network.
 * It's also a simplified version of {@link DetailNetworkResource}
 *
 * @param <R> The Response Type for creating a user
 * @author Carlos Vargas on 2/20/18.
 * @version 1.0.5
 * @see AppExecutors
 * @see MediatorLiveData
 * @see LiveData
 * @see ApiResponse
 * @see Resource
 * @since 1.0.5
 */
abstract class NetworkResource<R> {

    private val result: MediatorLiveData<Resource<R>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    /**
     * Inside this method you can define your own logic.
     */
    private fun fetchFromNetwork() {
        val apiResponseLiveData = createCall()
        setValue(Resource.loading(null))
        result.addSource(apiResponseLiveData) {
            result.removeSource(apiResponseLiveData)
            if (it != null) {
                if (it.isSuccessful()) {
                    setValue(Resource.success(it.body))
                } else {
                    setValue(Resource.error(it.errorMessage!!, null))
                }
            } else {
                setValue(Resource.error(ERROR_SERVICE_RESPONSE, null))
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<R>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData(): LiveData<Resource<R>> = result

    @NonNull
    @MainThread
    abstract fun createCall(): LiveData<ApiResponse<R>>

}