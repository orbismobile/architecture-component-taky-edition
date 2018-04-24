package com.elcomercio.mvvm_dagger_kotlin.repository.remote.api

import android.arch.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.BuildConfig
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Carlos Vargas on 4/12/18.
 * @author Carlo Renzo on 4/12/18.
 * @version 1.0.0
 * @since 1.0.0
 */
interface SampleApi {

    @GET(BuildConfig.USERS)
    fun getUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserResponse>>
}