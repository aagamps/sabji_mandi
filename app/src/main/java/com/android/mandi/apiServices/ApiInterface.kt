package com.android.mandi.apiServices

import com.android.mandi.dto.SabjiMandiDto
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&offset=1")
    fun getSabjiMandidata(): Single<SabjiMandiDto.Response>
}