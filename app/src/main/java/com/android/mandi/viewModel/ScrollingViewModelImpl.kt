package com.android.mandi.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mandi.apiServices.ApiRepository
import com.android.mandi.dto.SabjiMandiDto
import com.android.mandi.model.ScrollingModel
import javax.inject.Inject

class ScrollingViewModelImpl @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModelImpl(), ScrollingViewModel {

    var scrollingModel: ScrollingModel? = null
    override fun bindModel(model: ScrollingModel) {
        this.scrollingModel = model
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

    private val _sabjiMandiData = MutableLiveData<SabjiMandiDto.Response>()
    override fun showSabjiMadidata(): LiveData<SabjiMandiDto.Response> {
        return _sabjiMandiData
    }

    private val _sabjiMandiList = MutableLiveData<List<SabjiMandiDto.Record>>()
    override fun showSabjiMadiList(): LiveData<List<SabjiMandiDto.Record>> {
        return _sabjiMandiList
    }

    private val _locationList = MutableLiveData<List<String>>()
    override fun showLocationList(): LiveData<List<String>> {
        return _locationList
    }

    override fun getSabjiMandiNetworkData() {
        if (connectivityHelper.isConnected()) {
            _loader.value = true
            apiRepository.getSabjiMandidata()
                .subscribe(object : NetworkObserver<SabjiMandiDto.Response>() {
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onSucceed(response: SabjiMandiDto.Response) {
                        _sabjiMandiData.value = response
                        _loader.value = false
                        if (response.records != null) {
                            apiRepository.insertSabjiList(response.records!!)
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

    override fun getSabjiMandiList() {
        _loader.value = true
        apiRepository.getSabjiList()
            .subscribe(object : NetworkObserver<List<SabjiMandiDto.Record>>() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onSucceed(response: List<SabjiMandiDto.Record>) {
                    _sabjiMandiList.value = response
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

    override fun getLocationList() {
        _loader.value = true
        apiRepository.getLocationList()
            .subscribe(object : NetworkObserver<List<String>>() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onSucceed(response: List<String>) {
                    _locationList.value = response
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

