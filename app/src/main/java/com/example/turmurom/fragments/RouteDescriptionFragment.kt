package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentRouteDescriptionBinding

class RouteDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentRouteDescriptionBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRouteDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.route.observe(viewLifecycleOwner){
            binding.tvRouteDescription.text = it.description
            binding.tvDuration.text = it.duration
            binding.tvTitle.text = it.name
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RouteDescriptionFragment()
    }
}