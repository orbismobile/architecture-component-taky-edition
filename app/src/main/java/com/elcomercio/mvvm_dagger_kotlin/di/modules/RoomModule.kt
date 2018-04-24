package com.elcomercio.mvvm_dagger_kotlin.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.SampleRoomDatabase
import com.elcomercio.mvvm_dagger_kotlin.utils.AppExecutors
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDb(context: Application): SampleRoomDatabase =
            Room.databaseBuilder(
                    context.applicationContext,
                    SampleRoomDatabase::class.java,
                    "Sample.db")
                    .build()

    @Singleton
    @Provides
    fun provideUserDao(sampleRoomDatabase: SampleRoomDatabase) = sampleRoomDatabase.userDao()

    @Singleton
    @Provides
    fun provideAppExecutors() = AppExecutors(Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(THREAD_COUNT),
            AppExecutors.MainThreadExecutor())

    companion object {
        const val THREAD_COUNT = 3
    }

}