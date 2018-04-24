package com.elcomercio.mvvm_dagger_kotlin.repository.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao.UserDao
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class SampleRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}