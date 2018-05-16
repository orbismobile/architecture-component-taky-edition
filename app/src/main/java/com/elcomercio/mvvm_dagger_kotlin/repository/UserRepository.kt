package com.elcomercio.mvvm_dagger_kotlin.repository

import android.arch.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao.UserDao
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.SampleApi
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request.UserRequest
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserGetAllResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserGetResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserPostResponse
import com.elcomercio.mvvm_dagger_kotlin.utils.AppExecutors
import com.elcomercio.mvvm_dagger_kotlin.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The UserRepository class is responsible for handling specific data operations to User screen.
 *
 * @author Carlo Renzo on 18/12/17.
 * @author Carlos Vargas on 18/01/18.
 * @version 1.0.6
 * @see SampleApi
 * @see SampleApi#getBrandCombo(String)
 * @see UserDao
 * @see UserDao#getUsers()
 * @see UserDao#insertUser()
 * @see UserDao#updateUser()
 * @see UserDao#deleteUser()
 * @since 1.0.0
 */

@Singleton
class UserRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            private val userDao: UserDao,
            private val sampleApi: SampleApi) {

    //Requesting just Remote Data Sourcing
    fun getAllUsers(): LiveData<Resource<List<UserEntity>>> {
        return object : ProcessedNetworkResource<UserGetAllResponse, List<UserEntity>>() {
            override fun createCall(): LiveData<ApiResponse<UserGetAllResponse>> =
                    sampleApi.getUsers()

            override fun processResponse(response: UserGetAllResponse): List<UserEntity>? =
                    response.data.map {
                        UserEntity(it.id, it.firstName, it.lastName)
                    }
        }.asLiveData()
    }

    fun saveUserOnFromServer(userRequest: UserRequest): LiveData<Resource<UserEntity>> {
        return object : ProcessedNetworkResource<UserPostResponse, UserEntity>() {
            override fun createCall(): LiveData<ApiResponse<UserPostResponse>> =
                    sampleApi.postUsers(userRequest)

            override fun processResponse(response: UserPostResponse): UserEntity? =
                    UserEntity(response.data.insertId, userRequest.firstName, userRequest.lastName)
        }.asLiveData()
    }

    fun getUsers(userId: Int): LiveData<Resource<UserEntity>> =
            object : DetailNetworkResource<UserEntity, UserGetResponse>(appExecutors) {
                override fun saveCallResult(item: UserGetResponse) {
                    //MAPPING
                    val userEntity = UserEntity(item.data.id, item.data.firstName!!, item.data.lastName!!)

                    //Save
                    userDao.insertUser(userEntity)
                }

                override fun shouldFetch(data: UserEntity?): Boolean =
                        data == null

                override fun loadFromDb(): LiveData<UserEntity> =
                        userDao.getUser(userId)

                override fun createCall(): LiveData<ApiResponse<UserGetResponse>> =
                        sampleApi.getUsers(userId)

            }.asLiveData()
}