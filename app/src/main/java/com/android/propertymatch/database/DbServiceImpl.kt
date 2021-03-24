package com.android.propertymatch.database

import com.android.propertymatch.dto.PropertyMatchDto
import io.reactivex.Completable
import io.reactivex.Single

class DbServiceImpl constructor(database: PropertyMatchDatabase) : DbService {

    private val dao = database.propertymatchDao()

    override fun deleteAll(): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteFacilityList()
                dao.deleteOptionsList()
                dao.deleteExclusiveList()
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun insertFacilityList(records: List<PropertyMatchDto.FacilitiesObj>): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteFacilityList()
                dao.insertFacilityList(records)
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun getFacilityList(): Single<List<PropertyMatchDto.FacilitiesObj>> {
        return Single.create { emitter ->
            try {
                val list = dao.getFacilityList()
                emitter.onSuccess(list)
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun deleteFacilityList(): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteFacilityList()
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun insertOptionsList(records: List<PropertyMatchDto.OptionsObj>): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteOptionsList()
                dao.insertOptionsList(records)
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun getOptionsList(): Single<List<PropertyMatchDto.OptionsObj>> {
        return Single.create { emitter ->
            try {
                val list = dao.getOptionsList()
                emitter.onSuccess(list)
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun deleteOptionsList(): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteOptionsList()
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun insertExclusiveList(records: List<PropertyMatchDto.ExclusionsObj>): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteExclusiveList()
                dao.insertExclusiveList(records)
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun getExclusionsList(): Single<List<PropertyMatchDto.ExclusionsObj>> {
        return Single.create { emitter ->
            try {
                val list = dao.getExclusionsList()
                emitter.onSuccess(list)
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }

    override fun deleteExclusiveList(): Completable {
        return Completable.create { emitter ->
            try {
                dao.deleteExclusiveList()
                emitter.onComplete()
            } catch (exception: java.lang.Exception) {
                emitter.onError(exception)
            }
        }
    }


}