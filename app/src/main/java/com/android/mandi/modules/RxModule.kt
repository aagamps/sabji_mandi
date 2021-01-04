package com.android.mandi.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class RxModule {
    
    @Provides
    @Singleton
    @MainScheduler
    fun provideMainScheduler() : SchedulerProvider = MainSchedulerProvider()
    
    @Provides
    @Singleton
    @IoScheduler
    fun provideIoScheduler() : SchedulerProvider = IoSchedulerProvider()
}