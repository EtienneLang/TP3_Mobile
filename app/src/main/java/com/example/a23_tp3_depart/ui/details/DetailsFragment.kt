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
import com.example.a23_tp3_depart.ui.map.EditLocatDialogFragmentArgs

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(DetailsViewModel::class.java)

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = "Your Fragment Title"
        assert(arguments != null)
        val currentLocationId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        Log.d("TAG", "DANS DETAILS :" + currentLocationId)

        val viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.setContext(requireContext())

        viewModel.getLocatById(currentLocationId).observe(viewLifecycleOwner) { locat ->
            Log.d("TAG", "DANS DETAILS :" + locat.id)
            binding.tvNomDetails.text = locat.nom
            binding.tvAdresseDetails.text = locat.adresse
            binding.tvCategorieDetails.text = locat.categorie
            if (locat.categorie == "Maison") {
                binding.ivLocationBottom.setImageResource(R.drawable.maison)
            }
            else if (locat.categorie == "Travail") {
                binding.ivLocationBottom.setImageResource(R.drawable.travail)
            }
            else if (locat.categorie == "École") {
                binding.ivLocationBottom.setImageResource(R.drawable.ecole)
            }
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(locat.latitude, locat.longitude))
                .zoom(17f)
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(locat.latitude, locat.longitude))
                    .title(locat.nom)
            )
        }

        // Use mapFragment as needed
        // todo : titre de fragment dans l'ActionBar


        // todo : instanciation correcte de l'id d'élément détaillé


        // todo : régler le comportement de l'observe sur le point retourné par le view model
        // --> méthode onChanged de l'Observer : passer les valeurs du point courant à la View

        // todo : get mapFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap!!
        // todo : régler le comportement de l'observe sur élément Location retourné par le view model
        // --> méthode onChanged de l'Observer : affichage correct du marqueur pour ce point
    }
}