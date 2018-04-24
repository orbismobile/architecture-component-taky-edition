package com.elcomercio.mvvm_dagger_kotlin.di.component

import android.app.Application
import com.elcomercio.mvvm_dagger_kotlin.di.annotations.PerActivity
import com.elcomercio.mvvm_dagger_kotlin.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@PerActivity
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    RepositoryModule::class,
    RetrofitModule::class,
    RoomModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}