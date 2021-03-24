package com.android.propertymatch.apiServices

import com.android.propertymatch.dto.PropertyMatchDto
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET("/iranjith4/ad-assignment/db")
    fun getPropertyLiveData(): Single<PropertyMatchDto.Response>
}