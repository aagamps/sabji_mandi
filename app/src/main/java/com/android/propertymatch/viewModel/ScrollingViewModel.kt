package com.android.propertymatch.viewModel

import androidx.lifecycle.LiveData
import com.android.propertymatch.dto.PropertyMatchDto
import com.android.propertymatch.model.ScrollingModel

interface ScrollingViewModel {

    fun bindModel(model: ScrollingModel)

    fun getBoundModel(): ScrollingModel?

    fun showHideLoader(): LiveData<Boolean>

    fun showMessage(): LiveData<String>

    fun showHideLoaderWithProgress(): LiveData<Boolean>

    fun getPropertyLiveData()
    fun showPoetryLiveData(): LiveData<PropertyMatchDto.Response>

    fun getFacilityList()
    fun showPropertyFacilityData(): LiveData<List<PropertyMatchDto.FacilitiesObj>>

    fun getOptionsList()
    fun showPropertyOptionsData(): LiveData<List<PropertyMatchDto.OptionsObj>>

    fun getExclusionsList()
    fun showPropertyExclusionsData(): LiveData<List<PropertyMatchDto.ExclusionsObj>>

}