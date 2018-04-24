package com.elcomercio.mvvm_dagger_kotlin.di.modules

import com.elcomercio.mvvm_dagger_kotlin.repository.UserRepository
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao.UserDao
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.SampleApi
import com.elcomercio.mvvm_dagger_kotlin.utils.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(appExecutors: AppExecutors, userDao: UserDao, sampleApi: SampleApi) =
            UserRepository(appExecutors, userDao, sampleApi)
}