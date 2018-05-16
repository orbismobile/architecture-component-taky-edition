package com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
@Entity
data class UserEntity(@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val lastName: String)