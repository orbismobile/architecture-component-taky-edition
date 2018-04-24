package com.elcomercio.mvvm_dagger_kotlin.repository.remote.api

import retrofit2.Response

/**
 * This ApiResponse class is used for network request.
 * It's also a common class used by API responses.
 * It's a simple wrapper around {@link retrofit2.Call} class to convert its response into a
 * LiveData.
 *
 * @param <T> The essential entity body.
 * @author Carlos Vargas on 2/19/18
 * @version 1.0.5
 * @since 1.0.5
 */
class ApiResponse<T> {

    var code: Int = 0
    var body: T? = null
    var errorMessage: String? = null

    constructor(throwableError: Throwable) {
        code = 500
        body = null
        errorMessage = throwableError.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            body = null
            errorMessage = response.errorBody()?.string() ?: response.message()
        }
    }

    fun isSuccessful(): Boolean = code in 200..299
}