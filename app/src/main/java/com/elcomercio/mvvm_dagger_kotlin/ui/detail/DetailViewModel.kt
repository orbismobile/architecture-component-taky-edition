package com.elcomercio.mvvm_dagger_kotlin.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.elcomercio.mvvm_dagger_kotlin.repository.UserRepository
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.utils.AbsentLiveData
import com.elcomercio.mvvm_dagger_kotlin.utils.Resource
import javax.inject.Inject

class DetailViewModel
@Inject
constructor(private val userRepository: UserRepository) : ViewModel(){

    @VisibleForTesting
    private val userIdMutableLiveData: MutableLiveData<Int> = MutableLiveData()
    var userResourceLiveData: LiveData<Resource<UserEntity>>

    init {
        userResourceLiveData = Transformations.switchMap(userIdMutableLiveData, { userId ->
            if (userId == null || userId == 0) AbsentLiveData.create() else userRepository.getUsers(userId)
        })
    }

    fun loadUser(userId: Int) {
        if (userIdMutableLiveData.value != null && userIdMutableLiveData.value == userId) {
            return
        }
        userIdMutableLiveData.value = userId
    }

    fun retryLoadUser() {
        userIdMutableLiveData.value?.let {
            userIdMutableLiveData.value = userIdMutableLiveData.value
        }
    }
}