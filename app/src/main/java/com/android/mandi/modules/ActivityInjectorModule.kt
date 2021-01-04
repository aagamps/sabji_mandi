package com.android.mandi.modules

import com.android.mandi.activities.ScrollingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorModule {
    
    @ContributesAndroidInjector
    abstract fun bindScrollingActivity() : ScrollingActivity

}