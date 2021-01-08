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

    override fun getSabjiMandiList() {
        if (connectivityHelper.isConnected()) {
            apiRepository.getSabjiMandidata()
                .subscribe(object : NetworkObserver<SabjiMandiDto.Response>() {
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onSucceed(response: SabjiMandiDto.Response) {
                        _sabjiMandiData.value = response
                        if (response.records != null) {
                            apiRepository.deleteSabjiList()
                            apiRepository.insertSabjiList(response.records!!)
                        }
                        val rec = apiRepository.getSabjiList()
                        val aac = apiRepository.getSabjiList()
                    }

                    override fun onFailure(
                        errorCode: Int?,
                        status: String?,
                        errorMessage: String,
                        throwable: Throwable
                    ) {
                        _message.value = errorMessage
                    }
                })

        } else {
            _message.value = "No Internet Connection.."
        }
    }
}

