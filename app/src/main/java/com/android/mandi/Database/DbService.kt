package com.android.mandi.Database

import com.android.mandi.dto.SabjiMandiDto
import io.reactivex.Completable
import io.reactivex.Single

interface DbService {

    fun insertSabjiList(records: List<SabjiMandiDto.Record>): Completable

    fun getSabjiList(): Single<List<SabjiMandiDto.Record>>

    fun deleteSabjiList(): Completable

}