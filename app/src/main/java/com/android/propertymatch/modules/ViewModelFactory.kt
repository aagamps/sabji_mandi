package com.android.propertymatch.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.propertymatch.viewModel.ScrollingViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels : MutableMap<Class<out ViewModel>, Provider<ScrollingViewModel>>) :
    ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        val creator = viewModels[modelClass]
            ?: viewModels.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("ViewModelFactory: Unknown Model Class: $modelClass")
        return try {
            @Suppress("UNCHECKED_CAST")
            creator.get() as T
        } catch(e : Exception) {
            throw RuntimeException(e)
        }
    }
}