package com.elcomercio.mvvm_dagger_kotlin.util

import com.google.gson.Gson
import com.google.gson.JsonObject

object TestUtil {

    fun getJsonFromString(jsonBodyString: String): JsonObject {
        val gson = Gson()
        return gson.fromJson(jsonBodyString, JsonObject::class.java)
    }
}