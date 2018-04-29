package com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request

data class UserRequest(val firstName: String,
                       val lastName: String,
                       val email: String)