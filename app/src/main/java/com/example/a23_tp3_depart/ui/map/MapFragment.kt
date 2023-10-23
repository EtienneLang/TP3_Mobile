package com.example.a23_tp3_depart.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a23_tp3_depart.databinding.FragmentMapBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null

    val LOCATION_PERMISSION_CODE = 1

    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    // Position de l'utilisateur
    lateinit var userLocation: Location

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    // Déclaration pour le callback de la mise à jour de la position de l'utilisateur
    // Le callback est appelé à chaque fois que la position de l'utilisateur change
    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            userLocation = locationResult.lastLocation!!
            Log.d("***POSITION***", "onLocationResult: ${userLocation?.latitude} ${userLocation?.longitude}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(com.example.a23_tp3_depart.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //todo : clic sur fab
        // 1. activer la fonction d'ajout de points
        // 2. régler la couleur du fab

    }

    /**
     * Cette méthode est appelée lorsque l'utilisateur répond à la demande de permission.
     * @param requestCode Le code de la demande de permission
     * @param permissions La liste des permissions demandées
     * @param grantResults La liste des réponses de l'utilisateur pour chaque permission demandée (granted ou denied)
     */
    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                // Si la demande est annulée, les tableaux de résultats (grantResults) sont vides.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //todo : Permission accordée. Continuez l'action ou le flux de travail dans l'application.

                    // Si la permission est refusée, on affiche un message d'information
                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // Le système décide s'il faut afficher une explication supplémentaire
                    // À nouveau, vérification d'un requis de permission pour cette app
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        // On affiche une boîte de dialogue pour expliquer pourquoi la permission est requise
                        val dialog = AlertDialog.Builder(requireActivity())
                        dialog.setTitle("Permission requise !")
                        dialog.setMessage("Cette permission est importante pour la géolocalisation...")
                        dialog.setPositiveButton("Ok") { dialog, which ->
                            // On demande à nouveau la permission
                            ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.SEND_SMS),
                                LOCATION_PERMISSION_CODE
                            )
                        }
                        dialog.setNegativeButton("Annuler") { dialog, which ->
                            Toast.makeText(
                                requireActivity(),
                                "Pas de géolocalisation possible sans permission",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        dialog.show()
                    }
                }
            }
        }
    }

    /**
     * Permet d'activer la localisation de l'utilisateur
     */
    private fun enableMyLocation() {
        // vérification si la permission de localisation est déjà donnée
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (mMap != null) {
                // Permet d'afficher le bouton pour centrer la carte sur la position de l'utilisateur
                mMap.isMyLocationEnabled = true
            }
        } else {
            // La permission est manquante, demande donc la permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_CODE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        //todo : Vérification des permissions et positionnement si permission OK
        // normalement, ici, la demande de permission a déjà été traitée (onViewCreated)


        //todo : régler le comportement de l'observe sur la liste de points retourné par le view model
        // --> méthode onChanged de l'Observer : afficher les marqueurs sur la carte depuis la liste de tous les points
        // !!! Penser à passer le point courant au marqueur (setTag(Object)) à chaque ajout
        //     Ainsi le point est inclus dans le marqueur et accessible au getInfoContents(Marker)


        //todo : clic sur carte
        // 2 cas : Mode Ajout de Point et Mode normal


        //todo : placer la barre de zoom


        // Configuration du Layout pour les popups (InfoWindow)
        mMap.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val view: View = LayoutInflater.from(requireActivity()).inflate(com.example.a23_tp3_depart.R.layout.marker_layout, null)
                // todo : clic sur marqueur
                // 1. affichage de distance sur le fragment
                // 2. Déployer le layout de la vue Marker et passer les valeurs du point cliqué afin d'affichage

                return view
            }
        })



        // Active la localisation de l'utilisateur
        enableMyLocation()


        // Vérifie les permissions avant d'utiliser le service fusedLocationClient.getLastLocation()
        // qui permet de connaître la dernière position
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Demande la permission à l'utilisateur
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_CODE
            )
            // Si la permission n'est pas accordée, on ne va pas plus loin
            return
        }

        /**
         * Localisation
         */
        fusedLocationClient.lastLocation
            .addOnSuccessListener(requireActivity()) { location ->
                // Vérifie que la position n'est pas null
                if (location != null) {
                    Log.d("***POSITION***", "onSuccess: $location")
                    // Centre la carte sur la position de l'utilisateur au démarrage
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
                }
            }

        // Configuration pour mise à jour automatique de la position
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000L)
            .setMaxUpdateDelayMillis(5000L)
            .build()

        // Création de la requête pour la mise à jour de la position
        // avec la configuration précédente
        val request = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        // Création du client pour la mise à jour de la position.
        // Le client va permettre de vérifier si la configuration est correcte,
        // si l'utilisateur a activé ou désactivé la localisation
        val client = LocationServices.getSettingsClient(requireActivity())

        // Vérifie que la configuration de la mise à jour de la position est correcte
        // Si l'utilisateur a activé ou désactivé la localisation
        client.checkLocationSettings(request)
            .addOnSuccessListener {
                Log.d("***POSITION***", "onSuccess: $it")
                // Si la configuration est correcte, on lance la mise à jour de la position
                fusedLocationClient.requestLocationUpdates(
                    // Configuration de la mise à jour de la position
                    locationRequest,
                    // Callback pour la mise à jour de la position
                    locationCallback,
                    null
                )
            }
            .addOnFailureListener {
                Log.d("***POSITION***", "onFailure: $it")
                // Si la configuration n'est pas correcte, on affiche un message
                Toast.makeText(requireActivity(), "Veuillez activer la localisation", Toast.LENGTH_SHORT).show()
            }
        /**
         * Fin Localisation
         */


    }


}