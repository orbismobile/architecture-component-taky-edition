package com.elcomercio.mvvm_dagger_kotlin.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.SampleRoomDatabase
import org.junit.After
import org.junit.Before

open class SampleDbTest {

    lateinit var sampleRoomDatabase: SampleRoomDatabase

    @Before
    fun initDb() {
        sampleRoomDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                SampleRoomDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        sampleRoomDatabase.close()
    }

}