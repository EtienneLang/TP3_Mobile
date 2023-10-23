package com.example.a23_tp3_depart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location_table")
class Locat(
    @field:ColumnInfo(name = "name_col") var nom: String,
    var categorie: String,
    var adresse: String,
    var latitude: Double,
    var longitude: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
