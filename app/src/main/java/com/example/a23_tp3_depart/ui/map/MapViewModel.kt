package com.example.a23_tp3_depart.ui.map

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.a23_tp3_depart.data.LocDatabase
import com.example.a23_tp3_depart.model.Locat

class MapViewModel : ViewModel() {

    private lateinit var allLocations: LiveData<List<Locat>>
    private var mDb: LocDatabase? = null

    fun setContext(context: Context?) {
        mDb = LocDatabase.getInstance(context!!)
        allLocations = mDb?.locDao()!!.getAllLocations()
    }

    fun getAllLocations(): LiveData<List<Locat>> {
        return allLocations
    }

    fun insertLocation(location: Locat?) {
        mDb?.locDao()!!.insert(location)
    }
}