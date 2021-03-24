package com.android.mandi.apiServices

import com.android.mandi.database.DbService
import com.android.mandi.dto.PropertyMatchDto
import com.android.mandi.dto.SabjiMandiDto
import com.android.mandi.modules.IoScheduler
import com.android.mandi.modules.MainScheduler
import com.android.mandi.modules.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiInterface,
    private val dbService: DbService,
    @IoScheduler private val subscribeOnScheduler: SchedulerProvider,
    @MainScheduler private val observeOnScheduler: SchedulerProvider
) {

    internal fun getPropertyLiveData(): Single<PropertyMatchDto.Response> {
        return apiService.getPropertyLiveData().compose(applySchedulersSingle())
    }

    internal fun insertFacilityList(records: List<PropertyMatchDto.FacilitiesObj>): Completable {
        return dbService.insertFacilityList(records).compose(applySchedulersCompletable())
    }

    internal fun getFacilityList(): Single<List<PropertyMatchDto.FacilitiesObj>> {
        return dbService.getFacilityList().compose(applySchedulersSingle())
    }

    internal fun deleteFacilityList(): Completable {
        return dbService.deleteFacilityList()
    }

    internal fun insertOptionsList(records: List<PropertyMatchDto.OptionsObj>): Completable {
        return dbService.insertOptionsList(records).compose(applySchedulersCompletable())
    }

    internal fun getOptionsList(): Single<List<PropertyMatchDto.OptionsObj>> {
        return dbService.getOptionsList().compose(applySchedulersSingle())
    }

    internal fun deleteOptionsList(): Completable {
        return dbService.deleteOptionsList()
    }

    internal fun insertExclusiveList(records: List<PropertyMatchDto.ExclusionsObj>): Completable {
        return dbService.insertExclusiveList(records).compose(applySchedulersCompletable())
    }

    internal fun getExclusionsList(): Single<List<PropertyMatchDto.ExclusionsObj>> {
        return dbService.getExclusionsList().compose(applySchedulersSingle())
    }

    internal fun deleteExclusiveList(): Completable {
        return dbService.deleteExclusiveList()
    }



    internal fun deleteAll(): Completable {
        return dbService.deleteAll()
    }

    private fun <T> applySchedulersSingle(): SingleTransformer<T, T> {
        return SingleTransformer { single ->
            single.subscribeOn(subscribeOnScheduler.provideScheduler())
                .observeOn(observeOnScheduler.provideScheduler())
        }
    }

    private fun applySchedulersCompletable(): CompletableTransformer {
        return CompletableTransformer { completable ->
            completable.subscribeOn(subscribeOnScheduler.provideScheduler())
                .observeOn(observeOnScheduler.provideScheduler())
        }
    }
}