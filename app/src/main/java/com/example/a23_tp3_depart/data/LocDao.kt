package com.example.a23_tp3_depart.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a23_tp3_depart.model.Locat


@Dao
interface LocDao {
    //CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Locat?)

    @Update
    fun update(location: Locat?)

    @Query("DELETE FROM location_table")
    fun deleteAll()

    @Query("SELECT * FROM location_table WHERE `id` = :id")
    fun getLocation(id: Int): LiveData<Locat?>?

    @Delete
    fun deleteALoc(property: Locat?)

    @Query("SELECT * FROM location_table ORDER BY id DESC")
    fun getAllLocations() : LiveData<List<Locat>>

    @get:Query("SELECT COUNT(id) FROM location_table")
    val dataCount: Int
}
