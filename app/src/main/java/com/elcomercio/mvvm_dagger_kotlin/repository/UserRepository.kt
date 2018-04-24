package com.elcomercio.mvvm_dagger_kotlin.repository

import android.arch.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao.UserDao
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.SampleApi
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserResponse
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

    fun getUsers(userId: Int): LiveData<Resource<UserEntity>> =
            object : NetworkBoundResource<UserEntity, UserResponse>(appExecutors) {
                override fun saveCallResult(item: UserResponse) {
                    //MAPPING
                    val userEntity = UserEntity(item.id, item.name!!)

                    //Save
                    userDao.insertUser(userEntity)
                }

                override fun shouldFetch(data: UserEntity?): Boolean =
                        data == null

                override fun loadFromDb(): LiveData<UserEntity> =
                        userDao.getUser(userId)

                override fun createCall(): LiveData<ApiResponse<UserResponse>> =
                        sampleApi.getUsers(userId)

            }.asLiveData()

    fun saveUser(userEntity: UserEntity) {
        val saveRunnable = Runnable {
            userDao.insertUser(userEntity)
        }
        appExecutors.diskIO.execute(saveRunnable)
    }

    fun updateUser(userEntity: UserEntity) {
        val updateRunnable = Runnable {
            userDao.updateUser(userEntity)
        }
        appExecutors.diskIO.execute(updateRunnable)
    }

    fun deleteUser(userEntity: UserEntity) {
        val deleteRunnable = Runnable {
            userDao.deleteUser(userEntity)
        }
        appExecutors.diskIO.execute(deleteRunnable)
    }
}