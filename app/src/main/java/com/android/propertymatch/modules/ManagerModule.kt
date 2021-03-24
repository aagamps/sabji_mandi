package com.android.propertymatch.modules

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import androidx.room.Room
import com.android.propertymatch.constants.DB_NAME
import com.android.propertymatch.database.DbService
import com.android.propertymatch.database.DbServiceImpl
import com.android.propertymatch.database.PropertyMatchDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ManagerModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(application: Application): ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources = application.resources

    @Provides
    @Singleton
    fun provideContentResolver(application: Application): ContentResolver =
        application.contentResolver

    @Provides
    @Singleton
    fun provideDatabase(application: Application): PropertyMatchDatabase =
        Room.databaseBuilder(application, PropertyMatchDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun provideDatabaseService(database: PropertyMatchDatabase): DbService = DbServiceImpl(database)

}