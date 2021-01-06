@file:Suppress("unused")

package com.android.mandi

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.android.mandi.modules.DaggerComponents
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SabjiMandiApplication : DaggerApplication() {

    lateinit var sharedPreference: SharedPreferences

    companion object {
        @JvmStatic
        lateinit var webApp: SabjiMandiApplication
    }

    override fun onCreate() {
        super.onCreate()
        webApp = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val sabjiMandiComponent = DaggerComponents.builder()
            .application(this@SabjiMandiApplication)
            .build()
        sabjiMandiComponent.inject(this)
        return sabjiMandiComponent
    }

}