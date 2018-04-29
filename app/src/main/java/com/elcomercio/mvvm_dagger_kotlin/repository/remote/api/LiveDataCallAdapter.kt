package com.elcomercio.mvvm_dagger_kotlin.repository.remote.api

import android.arch.lifecycle.LiveData
import android.util.Log
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of {@link ApiResponse<R>}.
 *
 * @param <R> The response entity.
 * @author Carlos Vargas created on 2/19/18.
 * @version 1.0.5
 * @see Type
 * @see LiveData#postValue(Object)
 * @since 1.0.5
 */
class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> =
            object : LiveData<ApiResponse<R>>() {
                val started: AtomicBoolean = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<R> {

                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                postValue(ApiResponse(response))
                            }

                            override fun onFailure(call: Call<R>, throwable: Throwable) {
                                postValue(ApiResponse(throwable))
                            }
                        })
                    }
                }
            }

    override fun responseType(): Type = responseType
}