package com.android.mandi.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.mandi.dto.SabjiMandiDto

@Database(entities = [SabjiMandiDto.Record::class], version = 0, exportSchema = false)

abstract class SabjiMandiDatabase : RoomDatabase() {

    abstract fun sabjiMandiDao(): SabjiMandiDao
}