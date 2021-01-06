package com.android.mandi.modules

import androidx.lifecycle.ViewModelProvider
import com.android.mandi.activities.ScrollingActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityInjectorModule {
    
    @ContributesAndroidInjector
    abstract fun bindScrollingActivity() : ScrollingActivity

}