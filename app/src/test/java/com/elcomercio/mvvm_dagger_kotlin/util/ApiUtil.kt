package com.elcomercio.mvvm_dagger_kotlin.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import retrofit2.Response

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse(response)
    } as LiveData<ApiResponse<T>>
}