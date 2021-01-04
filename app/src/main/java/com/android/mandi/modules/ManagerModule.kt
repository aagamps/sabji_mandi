package com.android.mandi.modules

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import androidx.room.Room
import com.android.mandi.Constants.DB_NAME
import com.android.mandi.Database.DbService
import com.android.mandi.Database.DbServiceImpl
import com.android.mandi.Database.SabjiMandiDatabase
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
    fun provideDatabase(application: Application): SabjiMandiDatabase =
        Room.databaseBuilder(application, SabjiMandiDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun provideDatabaseService(database: SabjiMandiDatabase): DbService = DbServiceImpl(database)

}