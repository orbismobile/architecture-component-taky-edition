package com.elcomercio.mvvm_dagger_kotlin.di.annotations

import android.arch.lifecycle.ViewModel

import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by tohure on 22/12/17.
 */

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
