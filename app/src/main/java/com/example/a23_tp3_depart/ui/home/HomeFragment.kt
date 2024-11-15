package com.example.a23_tp3_depart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a23_tp3_depart.R
import com.example.a23_tp3_depart.data.LocDao
import com.example.a23_tp3_depart.data.LocDatabase
import com.example.a23_tp3_depart.databinding.FragmentHomeBinding
import com.example.a23_tp3_depart.model.Locat
import com.example.a23_tp3_depart.ui.map.MapViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel // Replace with your actual ViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        // Créera l'instance de la BD dans le ViewModel
        homeViewModel.setContext(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Déclaration du recyclerView et de l'adapteur
        recyclerView = view.findViewById(R.id.rv_location)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter()
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getAllLocations().observe(viewLifecycleOwner) { locats ->
            adapter.setLocations(locats)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}