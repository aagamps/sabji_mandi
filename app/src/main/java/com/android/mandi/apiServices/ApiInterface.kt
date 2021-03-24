package com.android.mandi.apiServices

import com.android.mandi.dto.PropertyMatchDto
import com.android.mandi.dto.SabjiMandiDto
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET("/iranjith4/ad-assignment/db")
    fun getPropertyLiveData(): Single<PropertyMatchDto.Response>
}