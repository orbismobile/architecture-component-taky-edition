package com.elcomercio.mvvm_dagger_kotlin.repository.remote.api

import android.arch.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.BuildConfig
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.DELETE

/**
 * @author Carlos Vargas on 4/12/18.
 * @author Carlo Renzo on 4/12/18.
 * @version 1.0.0
 * @since 1.0.0
 */
interface SampleApi {

    @GET(BuildConfig.GET_ALL_USERS)
    fun getUsers(): Call<UserGetAllResponse>

    @GET(BuildConfig.USERS)
    fun getUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserGetResponse>>

    @POST(BuildConfig.USERS)
    fun postUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserPostResponse>>

    @PUT(BuildConfig.USERS)
    fun putUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserPutResponse>>

    @DELETE(BuildConfig.USERS)
    fun deleteUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserDeleteResponse>>

}