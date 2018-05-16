package com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author carlosleonardocamilovargashuaman on 4/27/18.
 */
data class UserPostResponse(val status: String, val message: String, val data: DataBean) {

    data class DataBean(@SerializedName("insertId") val insertId: Int)
}