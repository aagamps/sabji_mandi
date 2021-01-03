package com.android.mandi.Database

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
                val flags = dao.getSabjiList()
                emitter.onSuccess(flags)
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
}