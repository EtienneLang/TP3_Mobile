package com.example.a23_tp3_depart.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a23_tp3_depart.R
import com.example.a23_tp3_depart.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.a23_tp3_depart.databinding.ActivityMainBinding
import com.example.a23_tp3_depart.ui.map.EditLocatDialogFragmentArgs

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    private lateinit var detailViewModel: DetailsViewModel // Remplacez par votre ViewModel réel

    private var currentLocationId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialisation du ViewModel
        val galleryViewModel =
            ViewModelProvider(this).get(DetailsViewModel::class.java)

        // Inflation de la mise en page du fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Récupération du fragment de la carte
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vérification de la présence d'arguments
        assert(arguments != null)
        // Récupération de l'ID de l'emplacement actuel depuis les arguments
        currentLocationId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        Log.d("TAG", "DANS DETAILS : $currentLocationId")

        // Initialisation du ViewModel
        detailViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        detailViewModel.setContext(requireContext())

        // Observation des modifications de l'emplacement actuel
        detailViewModel.getLocatById(currentLocationId).observe(viewLifecycleOwner) { locat ->
            Log.d("TAG", "DANS DETAILS : ${locat.id}")
            // Mise à jour des éléments d'interface utilisateur avec les informations de l'emplacement
            binding.tvNomDetails.text = locat.nom
            binding.tvAdresseDetails.text = locat.adresse
            binding.tvCategorieDetails.text = locat.categorie
            if (locat.categorie == "Maison") {
                binding.ivLocationBottom.setImageResource(R.drawable.maison)
                binding.bgImageDetails.setBackgroundColor(resources.getColor(R.color.bg_maison))
            } else if (locat.categorie == "Travail") {
                binding.ivLocationBottom.setImageResource(R.drawable.travail)
                binding.bgImageDetails.setBackgroundColor(resources.getColor(R.color.bg_travail))
            } else if (locat.categorie == "École") {
                binding.ivLocationBottom.setImageResource(R.drawable.ecole)
                binding.bgImageDetails.setBackgroundColor(resources.getColor(R.color.bg_ecole))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        detailViewModel.getLocatById(currentLocationId).observe(viewLifecycleOwner) { locat ->
            // Configuration de la position de la caméra
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(locat.latitude, locat.longitude))
                .zoom(17f)
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            // Ajout d'un marqueur sur la carte
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(locat.latitude, locat.longitude))
                    .title(locat.nom)
            )
        }
    }
}
