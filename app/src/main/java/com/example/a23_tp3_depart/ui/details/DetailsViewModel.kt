package com.example.a23_tp3_depart.ui.details

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.a23_tp3_depart.data.LocDao
import com.example.a23_tp3_depart.data.LocDatabase
import com.example.a23_tp3_depart.model.Locat

class DetailsViewModel : ViewModel() {

    private lateinit var location: LiveData<Locat>
    private var mDb: LocDatabase? = null

    fun setContext(context: Context?) {
        mDb = LocDatabase.getInstance(context!!)
    }

    // accédé par le fragment
    fun getLocatById(id: Int): LiveData<Locat> {
        // Use a database query to retrieve the Locat by ID
        location = mDb?.locDao()?.getLocation(id) as LiveData<Locat>
        return location
    }
}