package com.android.mandi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.mandi.constants.TABLE_EXCLUSIONS
import com.android.mandi.constants.TABLE_FACILITY
import com.android.mandi.constants.TABLE_OPTION
import com.android.mandi.constants.TABLE_SABJI_VERIATY
import com.android.mandi.dto.PropertyMatchDto

@Dao
interface SabjiMandiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacilityList(records: List<PropertyMatchDto.FacilitiesObj>)

    @Query("SELECT * FROM $TABLE_FACILITY")
    fun getFacilityList(): List<PropertyMatchDto.FacilitiesObj>

    @Query("DELETE FROM $TABLE_FACILITY")
    fun deleteFacilityList()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOptionsList(records: List<PropertyMatchDto.OptionsObj>)

    @Query("SELECT * FROM $TABLE_OPTION")
    fun getOptionsList(): List<PropertyMatchDto.OptionsObj>

    @Query("DELETE FROM $TABLE_OPTION")
    fun deleteOptionsList()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExclusiveList(records: List<PropertyMatchDto.ExclusionsObj>)

    @Query("SELECT * FROM $TABLE_EXCLUSIONS")
    fun getExclusionsList(): List<PropertyMatchDto.ExclusionsObj>

    @Query("DELETE FROM $TABLE_EXCLUSIONS")
    fun deleteExclusiveList()

}