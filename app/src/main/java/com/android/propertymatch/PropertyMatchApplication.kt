@file:Suppress("unused")

package com.android.propertymatch

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.android.propertymatch.modules.DaggerComponents
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PropertyMatchApplication : DaggerApplication() {

    lateinit var sharedPreference: SharedPreferences

    companion object {
        @JvmStatic
        lateinit var webApp: PropertyMatchApplication
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
        val propertyMatchComponent = DaggerComponents.builder()
            .application(this@PropertyMatchApplication)
            .build()
        propertyMatchComponent.inject(this)
        return propertyMatchComponent
    }

}