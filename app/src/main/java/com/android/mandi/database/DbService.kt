package com.android.mandi.database

import com.android.mandi.dto.PropertyMatchDto
import io.reactivex.Completable
import io.reactivex.Single

interface DbService {

    fun deleteAll(): Completable

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