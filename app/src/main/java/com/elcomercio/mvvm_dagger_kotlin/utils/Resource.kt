package com.elcomercio.mvvm_dagger_kotlin.utils

import com.elcomercio.mvvm_dagger_kotlin.utils.Status.SUCCESS
import com.elcomercio.mvvm_dagger_kotlin.utils.Status.LOADING
import com.elcomercio.mvvm_dagger_kotlin.utils.Status.ERROR

/**
 * @author carlosleonardocamilovargashuaman on 4/23/18.
 */
class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}