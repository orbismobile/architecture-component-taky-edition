package com.elcomercio.mvvm_dagger_kotlin.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao.UserDao
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.SampleApi
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserGetAllResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserGetResponse
import com.elcomercio.mvvm_dagger_kotlin.utils.AppExecutors
import com.elcomercio.mvvm_dagger_kotlin.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private val getUsersMutableLiveData = MutableLiveData<Resource<ApiResponse<UserGetAllResponse>>>()

    fun getUsers(): MutableLiveData<Resource<ApiResponse<UserGetAllResponse>>> {

        sampleApi.getUsers().enqueue(object : Callback<UserGetAllResponse> {
            override fun onFailure(call: Call<UserGetAllResponse>?, t: Throwable) {
                getUsersMutableLiveData.value = Resource.error(t.message!!, null)
            }

            override fun onResponse(call: Call<UserGetAllResponse>, response: Response<UserGetAllResponse>) {
                val apiResponse: ApiResponse<UserGetAllResponse> = ApiResponse(response)
                if (response.isSuccessful) {
                    getUsersMutableLiveData.value = Resource.success(apiResponse)
                } else {
                    getUsersMutableLiveData.value = Resource.error(apiResponse.errorMessage!!, null)
                }
            }

        })
        /*val response: Response<UserGetAllResponse> = sampleApi.getUsers().execute()
        val apiResponse: ApiResponse<UserGetAllResponse> = ApiResponse(response)
        if (apiResponse.isSuccessful()) {
            getUsersMutableLiveData.value = Resource.success(apiResponse)
        } else {
            getUsersMutableLiveData.value = Resource.error(apiResponse.errorMessage!!, null)
        }*/
        return getUsersMutableLiveData
    }

    fun getUsers(userId: Int): LiveData<Resource<UserEntity>> =
            object : NetworkBoundResource<UserEntity, UserGetResponse>(appExecutors) {
                override fun saveCallResult(item: UserGetResponse) {
                    //MAPPING
                    val userEntity = UserEntity(item.data!!.id, item.data!!.firstName!!)

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