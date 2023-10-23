package com.example.a23_tp3_depart.ui.map

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.a23_tp3_depart.R

class EditLocatDialogFragment(): DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        val inflater = requireActivity().layoutInflater

        arguments?.let {
            // todo : Récupère les arguments passés à la boîte de dialogue depuis l'activité appelante
            builder?.setTitle("Définir un nouveau point d'intérêt")
        }

        // Importe le layout de la boîte de dialogue
        // Le paramètre null est nécessaire car le layout est directement lié à la boîte de dialogue et non ancré dans un parent
        builder?.setView(inflater.inflate(R.layout.set_location_dialog, null))
            // Gestion des boutons Ok et Annuler
            ?.setPositiveButton("OK") { dialog, id ->
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