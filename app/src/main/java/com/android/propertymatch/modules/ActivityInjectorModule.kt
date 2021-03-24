package com.android.propertymatch.modules

import com.android.propertymatch.activities.ScrollingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityInjectorModule {
    
    @ContributesAndroidInjector
    abstract fun bindScrollingActivity() : ScrollingActivity

}