package com.elcomercio.mvvm_dagger_kotlin.repository.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE id = :userId")
    fun getUser(userId: Int): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)
}