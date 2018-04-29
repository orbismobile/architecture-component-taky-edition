package com.elcomercio.mvvm_dagger_kotlin.repository.remote.api

import android.arch.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.BuildConfig
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request.UserRequest
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.*
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Carlos Vargas on 4/12/18.
 * @author Carlo Renzo on 4/12/18.
 * @version 1.0.0
 * @since 1.0.0
 */
interface SampleApi {

    @GET(BuildConfig.USERS)
    fun getUsers(): Call<UserGetAllResponse>

    @GET(BuildConfig.USERS_ID)
    fun getUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserGetResponse>>

    @POST(BuildConfig.USERS)
    fun postUsers(@Body userRequest: UserRequest): Call<UserPostResponse>

    @PUT(BuildConfig.USERS_ID)
    fun putUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserPutResponse>>

    @DELETE(BuildConfig.USERS_ID)
    fun deleteUsers(@Path("userId") userId: Int): LiveData<ApiResponse<UserDeleteResponse>>

}