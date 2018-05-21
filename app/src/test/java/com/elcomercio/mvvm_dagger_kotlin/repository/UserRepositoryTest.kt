package com.elcomercio.mvvm_dagger_kotlin.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao.UserDao
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.SampleApi
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response.UserGetAllResponse
import com.elcomercio.mvvm_dagger_kotlin.util.ApiUtil
import com.elcomercio.mvvm_dagger_kotlin.util.InstantAppExecutors
import com.elcomercio.mvvm_dagger_kotlin.util.TestUtil.getJsonFromString
import com.elcomercio.mvvm_dagger_kotlin.util.mock
import com.elcomercio.mvvm_dagger_kotlin.utils.Resource
import okio.Okio
import com.google.gson.JsonObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.nio.charset.StandardCharsets

import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class UserRepositoryTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository

    private lateinit var jsonObjectBody: JsonObject

    private val userDao = mock(UserDao::class.java)

    private val sampleApi = mock(SampleApi::class.java)

    @Before
    @Throws(IOException::class, InterruptedException::class)
    fun setUp() {
        initJsonObject("brand_response_200.json")
        userRepository = UserRepository(InstantAppExecutors(), userDao, sampleApi)
    }


   @Test
   fun vemo(){
       val list : List<UserGetAllResponse.DataBean> = listOf()
       val userGetAllResponse = UserGetAllResponse("","", list)
       val userGetAllApiResponseLiveData: LiveData<ApiResponse<UserGetAllResponse>> = ApiUtil.successCall(userGetAllResponse)
       `when`(sampleApi.getUsers()).thenReturn(userGetAllApiResponseLiveData)

       val observer = mock<Observer<Resource<List<UserEntity>>>>()

       //val observer = mock<Resource<UserGetAllResponse>>()
       userRepository.getAllUsers().observeForever(observer)


   }

    @Throws(IOException::class)
    private fun initJsonObject(fileName: String) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api_response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val jsonBodyString = source.readString(StandardCharsets.UTF_8)
        jsonObjectBody = getJsonFromString(jsonBodyString)
    }
}