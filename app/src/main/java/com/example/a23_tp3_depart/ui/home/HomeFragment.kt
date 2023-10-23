package com.example.a23_tp3_depart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.a23_tp3_depart.data.LocDao
import com.example.a23_tp3_depart.data.LocDatabase
import com.example.a23_tp3_depart.databinding.FragmentHomeBinding
import com.example.a23_tp3_depart.model.Locat

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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

        // todo : déclaration et instanciation du RecyclerView
        // todo : configuration
        // todo : adapteur et passage de l'adapteur au RecyclerView


        // todo : régler le comportement de l'observe sur la liste de points retourné par le view model
        // --> méthode onChanged de l'Observer : passer la liste à l'adapteur

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}