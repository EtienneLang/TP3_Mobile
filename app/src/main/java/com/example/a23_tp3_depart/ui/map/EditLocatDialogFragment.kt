package com.example.a23_tp3_depart.ui.map

import android.app.Dialog
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.a23_tp3_depart.R
import com.example.a23_tp3_depart.data.LocDao
import com.example.a23_tp3_depart.data.LocDatabase
import com.example.a23_tp3_depart.model.Locat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class EditLocatDialogFragment() : DialogFragment() {
    private lateinit var locDat: LocDao
    private lateinit var database: LocDatabase
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.set_location_dialog, null)
        val spinner = view.findViewById<Spinner>(R.id.categoriesSpinner)
        val categories = resources.getStringArray(R.array.liste_categorie)
        val TvNom = view.findViewById<EditText>(R.id.txtNom)
        var adresse = String()
        val location = Location(null)
        val adapteur =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = adapteur
        database = LocDatabase.getInstance(requireContext())
        locDat = database.locDao()
        arguments?.let {
            // todo : Récupère les arguments passés à la boîte de dialogue depuis l'activité appelante
            location.latitude =
                EditLocatDialogFragmentArgs.fromBundle(requireArguments()).location.latitude
            location.longitude =
                EditLocatDialogFragmentArgs.fromBundle(requireArguments()).location.longitude
            adresse = EditLocatDialogFragmentArgs.fromBundle(requireArguments()).adresse
            Log.d("TAG - LATITUDE", location.latitude.toString())
            Log.d("TAG - LONGITUDE", location.longitude.toString())
            Log.d("TAG - ADRESSE", adresse)
            builder?.setTitle("Définir un nouveau point d'intérêt")
        }

        // Importe le layout de la boîte de dialogue
        // Le paramètre null est nécessaire car le layout est directement lié à la boîte de dialogue et non ancré dans un parent
        builder?.setView(view)
            // Gestion des boutons Ok et Annuler
            ?.setPositiveButton("OK") { dialog, id ->
                lifecycleScope.launch {
                    val locat = Locat(
                        TvNom.text.toString(),
                        spinner.selectedItem.toString(),
                        adresse,
                        location.latitude,
                        location.longitude
                    )
                    withContext(Dispatchers.IO) {
                        MapFragment.mapViewModel.insertLocation(locat)
                    }
                }
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                    Log.d("TAG", "onViewCreated ")
                }
                // todo : insertion du nouveau point d'intérêt dans la BD
            }
            ?.setNegativeButton("Annuler") { dialog, id ->
                getDialog()?.cancel()
            }
        if (builder != null) {
            return builder.create()
        }
        return super.onCreateDialog(savedInstanceState)
    }
}
