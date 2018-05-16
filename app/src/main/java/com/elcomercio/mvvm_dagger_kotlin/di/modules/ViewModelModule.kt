package com.elcomercio.mvvm_dagger_kotlin.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.elcomercio.mvvm_dagger_kotlin.di.annotations.ViewModelKey
import com.elcomercio.mvvm_dagger_kotlin.ui.user.UserViewModel
import com.elcomercio.mvvm_dagger_kotlin.viewmodel.SampleViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(sampleViewModelFactory: SampleViewModelFactory):
            ViewModelProvider.Factory
}