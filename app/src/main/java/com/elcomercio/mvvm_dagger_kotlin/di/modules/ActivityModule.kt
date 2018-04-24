package com.elcomercio.mvvm_dagger_kotlin.di.modules

import com.elcomercio.mvvm_dagger_kotlin.ui.user.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeUserActivity(): UserActivity

}