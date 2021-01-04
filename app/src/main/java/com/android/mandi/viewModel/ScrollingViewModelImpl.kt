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

    override fun showSabjiMadiList(): LiveData<SabjiMandiDto.Response> {
        TODO("Not yet implemented")
    }

    override fun getSabjiMandiList() {
        if (connectivityHelper.isConnected()) {
            _loader.value = true
            apiRepository.getSabjiMandidata()
                .subscribe(object : NetworkObserver<SabjiMandiDto.Response>() {
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onSucceed(response: SabjiMandiDto.Response) {
                        val json = gson.toJson(response)
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
            _loader.value = false
            _message.value = "No Internet Connection.."
        }
    }
}

