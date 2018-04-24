package com.elcomercio.mvvm_dagger_kotlin.app

import com.elcomercio.mvvm_dagger_kotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
class SampleApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}