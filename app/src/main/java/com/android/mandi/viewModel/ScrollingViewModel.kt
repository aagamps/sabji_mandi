package com.android.mandi.viewModel

import androidx.lifecycle.LiveData
import com.android.mandi.dto.SabjiMandiDto
import com.android.mandi.model.ScrollingModel

interface ScrollingViewModel {

    fun bindModel(model: ScrollingModel)

    fun getBoundModel(): ScrollingModel?

    fun showHideLoader(): LiveData<Boolean>

    fun showMessage(): LiveData<String>

    fun showHideLoaderWithProgress(): LiveData<Boolean>

    fun getSabjiMandiNetworkData()
    fun showSabjiMadidata(): LiveData<SabjiMandiDto.Response>

    fun getSabjiMandiList()
    fun showSabjiMadiList(): LiveData<List<SabjiMandiDto.Record>>

    fun getLocationList()
    fun showLocationList(): LiveData<List<String>>

}