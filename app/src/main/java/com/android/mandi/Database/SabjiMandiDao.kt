package com.android.mandi.Database

import androidx.room.Dao
import androidx.room.Query
import com.android.mandi.Constants.TABLE_SABJI_VERIATY
import com.android.mandi.dto.SabjiMandiDto

@Dao
interface SabjiMandiDao {

    @Query("SELECT * FROM $TABLE_SABJI_VERIATY")
    fun getSabjiList(): List<SabjiMandiDto.Record>

    @Query("DELETE FROM $TABLE_SABJI_VERIATY")
    fun deleteFSabjiList()
}