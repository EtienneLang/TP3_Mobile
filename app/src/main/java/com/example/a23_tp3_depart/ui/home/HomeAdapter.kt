package com.example.a23_tp3_depart.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.a23_tp3_depart.R
import com.example.a23_tp3_depart.model.Locat

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.NoteHolder>() {
    private var locations: List<Locat> = ArrayList<Locat>()
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_row, parent, false)
        context = parent.context
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentLocation: Locat = locations[position]
        val categorie: String = currentLocation.categorie
        holder.tvCategorie.text = categorie
        holder.tvNom.setText(currentLocation.nom)
        holder.tvAdresse.setText(currentLocation.adresse)
        if (categorie == "Maison") {
            holder.ivLocation.setImageResource(R.drawable.maison)
        }
        else if (categorie == "Travail") {
            holder.ivLocation.setImageResource(R.drawable.travail)
        }
        else if (categorie == "École") {
            holder.ivLocation.setImageResource(R.drawable.ecole)
        }
        else{
            //L'image de base va être celle de l'école à la place de mettre trop d'images, pas le but du tp.
            holder.ivLocation.setImageResource(R.drawable.maison)
        }
        Log.d("TAG", "DANS ADAPTER :" + currentLocation.id)
        val locationId = currentLocation.id

        //Navigation vers les détails d'un point d'intérêt
        holder.itemView.setOnClickListener { v ->
            val action: NavDirections =
                HomeFragmentDirections.actionNavHomeToNavDetails(locationId ?: 0)
            findNavController(v).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setLocations(locations: List<Locat>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategorie: TextView
        val tvNom: TextView
        val tvAdresse: TextView
        val ivLocation: ImageView

        init {
            tvCategorie = itemView.findViewById<TextView>(R.id.tv_categorie)
            tvNom = itemView.findViewById<TextView>(R.id.tv_nom)
            tvAdresse = itemView.findViewById<TextView>(R.id.tv_adresse)
            ivLocation = itemView.findViewById<ImageView>(R.id.iv_loc_cat)
        }
    }
}