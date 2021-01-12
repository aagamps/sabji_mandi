package com.android.mandi.database

import android.util.Log
import com.android.mandi.dto.SabjiMandiDto
import io.reactivex.Completable
import io.reactivex.Single

class DbServiceImpl constructor(database: SabjiMandiDatabase) : DbService {

    private val dao = database.sabjiMandiDao()

    override fun insertSabjiList(records: List<SabjiMandiDto.Record>): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteSabjiList()
                dao.insertSabjiList(records)
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun getSabjiList(): Single<List<SabjiMandiDto.Record>> {
        return Single.create { emitter ->
            try {
                val list = dao.getSabjiList()
                emitter.onSuccess(list)
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun deleteSabjiList(): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteSabjiList()
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun getLocationList(): Single<List<String>> {
        return Single.create { emitter ->
            try {
                val list = dao.getLocationList()
                emitter.onSuccess(list)
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }
}