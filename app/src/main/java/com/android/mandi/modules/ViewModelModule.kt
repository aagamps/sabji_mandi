package com.android.mandi.modules

import androidx.lifecycle.ViewModelProvider
import com.android.mandi.viewModel.ScrollingViewModel
import com.android.mandi.viewModel.ScrollingViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ScrollingViewModelImpl::class)
    internal abstract fun scrollingViewModelImplViewModelImpl(viewModel: ScrollingViewModelImpl): ScrollingViewModel
}