package com.android.mandi.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.mandi.constants.TABLE_SABJI_VERIATY
import com.google.gson.annotations.SerializedName

object SabjiMandiDto {

    data class Field(
        @SerializedName("id") var id: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("type") var type: String? = null
    )

    data class Response(
        @SerializedName("index_name") var indexName: String? = null,
        @SerializedName("title") var title: String? = null,
        @SerializedName("desc") var description: String? = null,
        @SerializedName("org_type") var orgType: String? = null,
        @SerializedName("org") var organization: List<String>? = null,
        @SerializedName("sector") var sector: List<String>? = null,
        @SerializedName("source") var source: String? = null,
        @SerializedName("catalog_uuid") var catalogUuid: String? = null,
        @SerializedName("visualizable") var visualizable: String? = null,
        @SerializedName("created") var created: Int? = null,
        @SerializedName("updated") var updated: Int? = null,
        @SerializedName("created_date") var createdDate: String? = null,
        @SerializedName("updated_date") var updatedDate: String? = null,
        @SerializedName("target_bucket") var targetBucket: Map<String, String>? = null,
        @SerializedName("field") var fields: List<Field>? = null,
        @SerializedName("records") var records: List<Record>? = null
    )

    @Entity(tableName = TABLE_SABJI_VERIATY)
    data class Record(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @SerializedName("timestamp") var timestamp: String? = null,
        @SerializedName("state") var state: String? = null,
        @SerializedName("district") var district: String? = null,
        @SerializedName("market") var market: String? = null,
        @SerializedName("commodity") var commodity: String? = null,
        @SerializedName("variety") var variety: String? = null,
        @SerializedName("arrival_date") var arrivalDate: String? = null,
        @SerializedName("min_price") var minPrice: String? = null,
        @SerializedName("max_price") var maxPrice: String? = null,
        @SerializedName("modal_price") var modalPrice: String? = null,
        var location: String? = null
    )

}