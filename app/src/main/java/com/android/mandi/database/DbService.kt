package com.android.mandi.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.mandi.constants.TABLE_EXCLUSIONS
import com.android.mandi.constants.TABLE_OPTION
import com.android.mandi.dto.PropertyMatchDto
import com.android.mandi.dto.SabjiMandiDto
import io.reactivex.Completable
import io.reactivex.Single

interface DbService {

    fun insertSabjiList(records: List<SabjiMandiDto.Record>): Completable

    fun getSabjiList(): Single<List<SabjiMandiDto.Record>>

    fun deleteAll(): Completable

    fun getLocationList(): Single<List<String>>


    fun insertFacilityList(records: List<PropertyMatchDto.FacilitiesObj>): Completable

    fun getFacilityList():  Single<List<PropertyMatchDto.FacilitiesObj>>

    fun deleteFacilityList(): Completable


    fun insertOptionsList(records: List<PropertyMatchDto.OptionsObj>): Completable

    fun getOptionsList(): Single<List<PropertyMatchDto.OptionsObj>>

    fun deleteOptionsList(): Completable


    fun insertExclusiveList(records: List<PropertyMatchDto.ExclusionsObj>): Completable

    fun getExclusionsList(): Single<List<PropertyMatchDto.ExclusionsObj>>

    fun deleteExclusiveList(): Completable
}