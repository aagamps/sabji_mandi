package com.android.propertymatch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.propertymatch.dto.PropertyMatchDto

@Database(
    entities = [PropertyMatchDto.FacilitiesObj::class,
        PropertyMatchDto.OptionsObj::class,
        PropertyMatchDto.ExclusionsObj::class],
    version = 1,
    exportSchema = false
)

abstract class PropertyMatchDatabase : RoomDatabase() {

    abstract fun propertymatchDao(): PropertymatchDao

}