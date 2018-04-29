package com.elcomercio.mvvm_dagger_kotlin.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.WorkerThread
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
 * <p>For information about how NetworkBoundResource class works, read the official documentation:
 * <a href="https://developer.android.com/topic/libraries/architecture/guide.html#addendum">
 * Addendum: exposing network status.</a>
 * </p>
 * </div>
 * This class fetch the result from network.
 * It's also a simplified version of {@link NetworkBoundResource}
 *
 * @param <R> Type for the Resource data
 * @param <T> Type for the API response
 * @author Carlos Vargas on 2/20/18.
 * @version 1.0.5
 * @see AppExecutors
 * @see MediatorLiveData
 * @see LiveData
 * @see ApiResponse
 * @see Resource
 * @since 1.0.5
 */
abstract class NetworkBoundResource<R, T>(private val appExecutors: AppExecutors) {

    private val result: MediatorLiveData<Resource<R>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        val dbSource: LiveData<R> = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) { //From Network resource
                fetchFromNetwork(dbSource)
            } else { //From Database

                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<R>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<R>) {
        val apiResponseLiveData = createCall()
        result.addSource(dbSource) { newData -> setValue(Resource.loading(newData)) }
        result.addSource(apiResponseLiveData) { apiResponse ->
            result.removeSource(apiResponseLiveData)
            result.removeSource(dbSource)

            if (apiResponse != null) {
                if (apiResponse.isSuccessful()) {
                    appExecutors.diskIO.execute({
                        saveCallResult(processResponse(apiResponse)!!)
                        appExecutors.mainThread.execute({
                            result.addSource(loadFromDb(), { newData ->
                                setValue(Resource.success(newData))
                            })
                        })
                    })
                } else {
                    result.addSource(dbSource
                    ) { newData -> setValue(Resource.error(apiResponse.errorMessage!!, newData)) }
                }
            } else {
                setValue(Resource.error(ERROR_SERVICE_RESPONSE, null))
            }
        }
    }

    fun asLiveData(): LiveData<Resource<R>> = result

    @WorkerThread
    private fun processResponse(response: ApiResponse<T>) = response.body

    @WorkerThread
    abstract fun saveCallResult(item: T)

    @MainThread
    abstract fun shouldFetch(data: R?): Boolean

    @NonNull
    @MainThread
    abstract fun loadFromDb(): LiveData<R>

    @NonNull
    @MainThread
    abstract fun createCall(): LiveData<ApiResponse<T>>

}