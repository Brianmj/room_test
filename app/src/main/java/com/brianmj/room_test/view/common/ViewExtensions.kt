package com.brianmj.room_test.view.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>) : T =
        ViewModelProviders.of(this).get(modelClass.java)