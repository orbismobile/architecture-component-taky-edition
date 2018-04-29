package com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
data class UserGetResponse(val status: String, val message: String, val data: DataBean) {
    data class DataBean(val id: Int,
                        @SerializedName("firstName") val firstName: String?,
                        @SerializedName("lastName") val lastName: String?,
                        val email: String)
}

