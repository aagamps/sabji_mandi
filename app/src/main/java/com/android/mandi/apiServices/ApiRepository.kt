package com.android.mandi.apiServices

import com.android.mandi.database.DbService
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

    internal fun getSabjiMandidata(): Single<SabjiMandiDto.Response> {
        return apiService.getSabjiMandidata().compose(applySchedulersSingle())
    }

    internal fun insertSabjiList(records: List<SabjiMandiDto.Record>): Completable {
        return dbService.insertSabjiList(records).compose(applySchedulersCompletable())
    }

    internal fun getSabjiList(): Single<List<SabjiMandiDto.Record>> {
        return dbService.getSabjiList().compose(applySchedulersSingle())
    }

    internal fun getLocationList(): Single<List<String>> {
        return dbService.getLocationList().compose(applySchedulersSingle())
    }

    internal fun deleteSabjiList(): Completable {
        return dbService.deleteSabjiList()
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