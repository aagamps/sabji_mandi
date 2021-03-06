package com.android.mandi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.mandi.dto.SabjiMandiDto

@Database(entities = [SabjiMandiDto.Record::class], version = 1, exportSchema = false)

abstract class SabjiMandiDatabase : RoomDatabase() {

    abstract fun sabjiMandiDao(): SabjiMandiDao
}