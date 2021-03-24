package com.android.mandi.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mandi.apiServices.ApiRepository
import com.android.mandi.dto.PropertyMatchDto
import com.android.mandi.model.ScrollingModel
import javax.inject.Inject

class ScrollingViewModelImpl @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModelImpl(), ScrollingViewModel {

    var scrollingModel: ScrollingModel? = null
    override fun bindModel(model: ScrollingModel) {
       scrollingModel = model
    }

    override fun getBoundModel(): ScrollingModel? {
        return scrollingModel
    }

    private val _loader = MutableLiveData<Boolean>()
    override fun showHideLoader(): LiveData<Boolean> {
        return _loader
    }

    private val _message = MutableLiveData<String>()
    override fun showMessage(): LiveData<String> {
        return _message
    }

    private val _loaderWithProgress = MutableLiveData<Boolean>()
    override fun showHideLoaderWithProgress(): LiveData<Boolean> {
        return _loaderWithProgress
    }

    private val _propertyLiveData = MutableLiveData<PropertyMatchDto.Response>()
    override fun showPoetryLiveData(): LiveData<PropertyMatchDto.Response> {
        return _propertyLiveData
    }

    private val _propertyOptionsList = MutableLiveData<List<PropertyMatchDto.OptionsObj>>()
    override fun showPropertyOptionsData(): LiveData<List<PropertyMatchDto.OptionsObj>> {
        return _propertyOptionsList
    }

    private val _propertyFacilityList = MutableLiveData<List<PropertyMatchDto.FacilitiesObj>>()
    override fun showPropertyFacilityData(): LiveData<List<PropertyMatchDto.FacilitiesObj>> {
        return _propertyFacilityList
    }

    private val _propertyExclusionsList = MutableLiveData<List<PropertyMatchDto.ExclusionsObj>>()
    override fun showPropertyExclusionsData(): LiveData<List<PropertyMatchDto.ExclusionsObj>> {
        return _propertyExclusionsList
    }

    override fun getPropertyLiveData() {
        if (connectivityHelper.isConnected()) {
            _loader.value = true
            apiRepository.getPropertyLiveData()
                .subscribe(object : NetworkObserver<PropertyMatchDto.Response>() {
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onSucceed(response: PropertyMatchDto.Response) {
                        _propertyLiveData.value = response
                        _loader.value = false
                        if (response.facilities != null) {
                            apiRepository.insertFacilityList(response.facilities!!)
                                .subscribe(object : NetworkObserver<Void>() {
                                    override fun onCompleted() = Unit

                                    override fun onFailure(
                                        errorCode: Int?,
                                        status: String?,
                                        errorMessage: String,
                                        throwable: Throwable
                                    ) = Unit
                                })

                            val optionsList = ArrayList<PropertyMatchDto.OptionsObj>()
                            for (indexFacility in response.facilities!!) {
                                for (indexOptions in indexFacility.options!!) {
                                    val options = PropertyMatchDto.OptionsObj(
                                        facilityId = indexFacility.facilityId,
                                        facilityName = indexFacility.name,
                                        name = indexOptions.name,
                                        icon = indexOptions.icon,
                                        optionsIId = indexOptions.optionsIId
                                    )
                                    optionsList.add(options)
                                }
                            }
                            apiRepository.insertOptionsList(optionsList)
                                .subscribe(object : NetworkObserver<Void>() {
                                    override fun onCompleted() = Unit

                                    override fun onFailure(
                                        errorCode: Int?,
                                        status: String?,
                                        errorMessage: String,
                                        throwable: Throwable
                                    ) = Unit
                                })
                        }

                        if (response.exclusions != null) {
                            val exclusionsList = ArrayList<PropertyMatchDto.ExclusionsObj>()
                            for (indexOuter in response.exclusions!!) {
                                for (indexInner in indexOuter) {
                                    val exclusion = PropertyMatchDto.ExclusionsObj(
                                        pairId = indexOuter.toString(),
                                        facilityId = indexInner.facilityId,
                                        optionsIId = indexInner.optionsIId
                                    )
                                    exclusionsList.add(exclusion)
                                }
                            }
                            apiRepository.insertExclusiveList(exclusionsList)
                                .subscribe(object : NetworkObserver<Void>() {
                                    override fun onCompleted() = Unit

                                    override fun onFailure(
                                        errorCode: Int?,
                                        status: String?,
                                        errorMessage: String,
                                        throwable: Throwable
                                    ) = Unit
                                })
                        }

                    }

                    override fun onFailure(
                        errorCode: Int?,
                        status: String?,
                        errorMessage: String,
                        throwable: Throwable
                    ) {
                        _message.value = errorMessage
                        _loader.value = false
                    }
                })

        } else {
            _message.value = "Internet is not available.."
            _loader.value = false
        }
    }

    override fun getFacilityList() {
        _loader.value = true
        apiRepository.getFacilityList()
            .subscribe(object : NetworkObserver<List<PropertyMatchDto.FacilitiesObj>>() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onSucceed(response: List<PropertyMatchDto.FacilitiesObj>) {
                    scrollingModel?.facilityList = response
                    _propertyFacilityList.value = response
                    _loader.value = false
                }

                override fun onFailure(
                    errorCode: Int?,
                    status: String?,
                    errorMessage: String,
                    throwable: Throwable
                ) {
                    _message.value = errorMessage
                    _loader.value = false
                }
            })
    }

    override fun getOptionsList() {
        _loader.value = true
        apiRepository.getOptionsList()
            .subscribe(object : NetworkObserver<List<PropertyMatchDto.OptionsObj>>() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onSucceed(response: List<PropertyMatchDto.OptionsObj>) {
                    scrollingModel?.optionsList = response
                    _propertyOptionsList.value = response
                    _loader.value = false
                }

                override fun onFailure(
                    errorCode: Int?,
                    status: String?,
                    errorMessage: String,
                    throwable: Throwable
                ) {
                    _message.value = errorMessage
                    _loader.value = false
                }
            })
    }

    override fun getExclusionsList() {
        _loader.value = true
        apiRepository.getExclusionsList()
            .subscribe(object : NetworkObserver<List<PropertyMatchDto.ExclusionsObj>>() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onSucceed(response: List<PropertyMatchDto.ExclusionsObj>) {
                    scrollingModel?.exclusionsList = response
                    _propertyExclusionsList.value = response
                    _loader.value = false
                }

                override fun onFailure(
                    errorCode: Int?,
                    status: String?,
                    errorMessage: String,
                    throwable: Throwable
                ) {
                    _message.value = errorMessage
                    _loader.value = false
                }
            })
    }

}

