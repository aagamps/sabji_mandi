package com.android.propertymatch.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.android.propertymatch.common.ConnectivityHelperImpl
import com.android.propertymatch.dto.ErrorDto
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseViewModelImpl : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    @Inject
    protected lateinit var gson: Gson

    @Inject
    protected lateinit var connectivityHelper: ConnectivityHelperImpl


    fun addDisposable(vararg disposable: Disposable) {
        compositeDisposable.addAll(*disposable)
    }
    

    abstract inner class NetworkObserver<T> : io.reactivex.SingleObserver<T>,
        io.reactivex.CompletableObserver {

        final override fun onSubscribe(disposable: Disposable) {
            addDisposable(disposable)
        }

        final override fun onSuccess(response: T) {
            onSucceed(response)
        }

        final override fun onComplete() {
            onCompleted()
        }

        @SuppressLint("DefaultLocale")
        final override fun onError(throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val errorCode = throwable.code()
                    val errorMessage = throwable.response()?.errorBody()?.string()
                    if (!errorMessage.isNullOrBlank()) {
                        try {
                            val errorDto = gson.fromJson(errorMessage, ErrorDto::class.java)
                            val status = errorDto.status
                            val serverMessage = errorDto.message
                            if (!status.isNullOrBlank() && !serverMessage.isNullOrBlank()) {
                                onFailure(errorCode, status.toLowerCase(), serverMessage, throwable)
                            } else {
                                onFailure(
                                    errorCode,
                                    null,
                                    "Oops.!! Something went wrong",
                                    throwable
                                )
                            }
                        } catch (exception: Exception) {
                            onFailure(
                                errorCode,
                                null,
                                "Oops.!! Something went wrong",
                                throwable
                            )
                        }
                    } else {
                        onFailure(
                            errorCode,
                            null,
                            "Oops.!! Something went wrong",
                            throwable
                        )
                    }
                }
                else -> {
                    val errorMessage = throwable.message
                    if (!errorMessage.isNullOrBlank()) {
                        onFailure(null, null, errorMessage, throwable)
                    } else {
                        onFailure(
                            null,
                            null,
                            "Oops.!! Something went wrong",
                            throwable
                        )
                    }
                }
            }
        }

        protected open fun onSucceed(response: T) = Unit
        protected open fun onCompleted() = Unit
        protected open fun onFailure(
            errorCode: Int?,
            status: String?,
            errorMessage: String,
            throwable: Throwable
        ) {
            onFailure(status, errorMessage, throwable)
        }

        protected open fun onFailure(status: String?, errorMessage: String, throwable: Throwable) =
            Unit
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }
}