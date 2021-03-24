package com.android.mandi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.mandi.dto.PropertyMatchDto

@Database(
    entities = [PropertyMatchDto.FacilitiesObj::class,
        PropertyMatchDto.OptionsObj::class,
        PropertyMatchDto.ExclusionsObj::class],
    version = 1,
    exportSchema = false
)

abstract class SabjiMandiDatabase : RoomDatabase() {

    abstract fun sabjiMandiDao(): SabjiMandiDao

}