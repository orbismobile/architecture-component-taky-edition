package com.elcomercio.mvvm_dagger_kotlin.util

import com.elcomercio.mvvm_dagger_kotlin.utils.AppExecutors

import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}