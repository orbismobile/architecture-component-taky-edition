package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.elcomercio.mvvm_dagger_kotlin.repository.UserRepository
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request.UserRequest
import com.elcomercio.mvvm_dagger_kotlin.utils.AbsentLiveData
import com.elcomercio.mvvm_dagger_kotlin.utils.Resource
import javax.inject.Inject

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
class UserViewModel
@Inject
constructor(private val userRepository: UserRepository) : ViewModel() {

    @VisibleForTesting
    private val allUsersMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var getAllUsersResourceLiveData: LiveData<Resource<List<UserEntity>>>

    @VisibleForTesting
    private val userRequestMutableLiveData: MutableLiveData<UserRequest> = MutableLiveData()
    var postUserResourceLiveData: LiveData<Resource<UserEntity>>

    @VisibleForTesting
    private val userIdMutableLiveData: MutableLiveData<Int> = MutableLiveData()
    var getSpecificUserResourceLiveData: LiveData<Resource<UserEntity>>

    init {
        //GET ALL USERS
        getAllUsersResourceLiveData = Transformations.switchMap(allUsersMutableLiveData, {
            if (it == false) AbsentLiveData.create() else userRepository.getAllUsers()
        })

        //POST NEW USER
        postUserResourceLiveData = Transformations.switchMap(userRequestMutableLiveData, { userRequest ->
            if (userRequest == null) AbsentLiveData.create()
            else userRepository.saveUserOnFromServer(userRequest)
        })

        //GET SPECIFIC USER
        getSpecificUserResourceLiveData = Transformations.switchMap(userIdMutableLiveData, { userId ->
            if (userId == null || userId == 0) AbsentLiveData.create()
            else userRepository.getUsers(userId)
        })
    }

    //METHODS USED IN USER ACTIVITY
    /**
     *  Load all users from server.
     */
    fun loadAllUsers(fetchAllUsers: Boolean) {
        if (allUsersMutableLiveData.value == fetchAllUsers) {
            return
        }
        allUsersMutableLiveData.value = fetchAllUsers
    }

    /**
     * If something was wrong with the @link{loadAllUsers()} method, then we can retry the request.
     */
    fun retryLoadAllUsers() {
        allUsersMutableLiveData.value?.let {
            allUsersMutableLiveData.value = allUsersMutableLiveData.value
        }
    }

    /**
     * Save a new user.
     */
    fun saveUserOnFromServer(userRequest: UserRequest) {
        userRequestMutableLiveData.value = userRequest
    }

    //METHODS USED IN DETAIL ACTIVITY
    /**
     * Fetch a user by their id
     */
    fun loadUser(userId: Int) {
        if (userIdMutableLiveData.value != null && userIdMutableLiveData.value == userId) {
            return
        }
        userIdMutableLiveData.value = userId
    }

    /**
     * If something was wrong with the @link{loadUser()} method, then we can retry the request.
     */
    fun retryLoadUser() {
        userIdMutableLiveData.value?.let {
            userIdMutableLiveData.value = userIdMutableLiveData.value
        }
    }
}