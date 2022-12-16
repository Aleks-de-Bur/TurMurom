package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentExcursionDescriptionBinding

class ExcursionDescriptionFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private lateinit var binding: FragmentExcursionDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExcursionDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.excursion.observe(viewLifecycleOwner){
            binding.tvExcursionDescription.text = it.description
        }
    }

    companion object{
        @JvmStatic
        fun newInstance() = ExcursionDescriptionFragment()
    }
}