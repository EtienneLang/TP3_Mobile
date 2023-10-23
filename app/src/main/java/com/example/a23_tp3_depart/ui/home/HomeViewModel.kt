package com.example.a23_tp3_depart.ui.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.a23_tp3_depart.data.LocDatabase
import com.example.a23_tp3_depart.model.Locat
import com.example.a23_tp3_depart.data.LocDao

class HomeViewModel : ViewModel() {

    private lateinit var allLocations: LiveData<List<Locat>>

    private var mDb: LocDatabase? = null

    @SuppressLint("NotConstructor")
    fun setContext(context: Context?) {
        mDb = LocDatabase.getInstance(context!!.applicationContext)
        allLocations = mDb?.locDao()!!.getAllLocations()
    }

    fun getAllLocations(): LiveData<List<Locat>> {
        return allLocations
    }
}