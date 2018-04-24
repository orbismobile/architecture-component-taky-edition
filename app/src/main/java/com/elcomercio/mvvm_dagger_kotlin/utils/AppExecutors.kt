package com.elcomercio.mvvm_dagger_kotlin.utils

import java.util.concurrent.Executor
import javax.inject.Singleton

import android.os.Handler
import android.os.Looper

/**
 * @author Carlos Vargas on 4/20/18.
 */
@Singleton
class AppExecutors(val diskIO: Executor, val networkIO: Executor, val mainThread: Executor) {

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}