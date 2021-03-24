package com.android.mandi.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.android.mandi.constants.TABLE_EXCLUSIONS
import com.android.mandi.constants.TABLE_FACILITY
import com.android.mandi.constants.TABLE_OPTION
import com.google.gson.annotations.SerializedName

object PropertyMatchDto {

    data class Response(
        @SerializedName("facilities") var facilities: ArrayList<FacilitiesObj>? = null,
        @SerializedName("exclusions") var exclusions: ArrayList<ArrayList<ExclusionsObj>>? = null
    )

    @Entity(tableName = TABLE_FACILITY)
    data class FacilitiesObj(
        @PrimaryKey(autoGenerate = true) var index: Int = 0,
        @SerializedName("facility_id") var facilityId: String? = null,
        @SerializedName("name") var name: String? = null,
        @Ignore @SerializedName("options") var options: ArrayList<OptionsObj> = ArrayList()
    )

    @Entity(tableName = TABLE_OPTION)
    data class OptionsObj(
        @PrimaryKey(autoGenerate = true) var index: Int = 0,
        var facilityId: String? = null,
        var facilityName: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("icon") var icon: String? = null,
        @SerializedName("id") var optionsIId: String? = null,
        @Ignore var isEnabled: Boolean = true,
        @Ignore var isChecked: Boolean = false
    )

    @Entity(tableName = TABLE_EXCLUSIONS)
    data class ExclusionsObj(
        @PrimaryKey(autoGenerate = true) var index: Int = 0,
        var pairId: String? = null,
        @SerializedName("facility_id") var facilityId: String? = null,
        @SerializedName("options_id") var optionsIId: String? = null
    )

}