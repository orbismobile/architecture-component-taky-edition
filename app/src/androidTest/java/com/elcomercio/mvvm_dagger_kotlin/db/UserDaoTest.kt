package com.elcomercio.mvvm_dagger_kotlin.db

import android.support.test.runner.AndroidJUnit4
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.util.LiveDataTestUtil
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class UserDaoTest: SampleDbTest() {

    private val userEntityList: MutableList<UserEntity> = mutableListOf()

    private val userEntity = UserEntity(FAKE_USER_ID, FAKE_USER_NAME, FAKE_USER_LASTNAME)

    companion object {
        const val FAKE_USER_ID = 0
        const val FAKE_USER_NAME = "ORBIS"
        const val FAKE_USER_LASTNAME = "MOBILE"
    }

    @Before
    fun setUp(){
        userEntityList.add(UserEntity(FAKE_USER_ID, FAKE_USER_NAME, FAKE_USER_LASTNAME))
    }

    @Test
    fun insertAndReadUserEntityListAndValidateListNotEmpty(){
        sampleRoomDatabase.userDao().insertUser(userEntity)

        val userEntityList = LiveDataTestUtil.getValue(sampleRoomDatabase.userDao().getUsers())
        assertNotNull("UserEntityList must not be null.", userEntityList)
    }

    @Test
    fun insertAndReadUserEntityAndValidateQuantityOfElementsReturned(){
        sampleRoomDatabase.userDao().insertUser(userEntity)

        val userEntityList = LiveDataTestUtil.getValue(sampleRoomDatabase.userDao().getUsers())
        assertEquals("UserEntityList size must not be 1.", userEntityList.size, 1)
    }
}