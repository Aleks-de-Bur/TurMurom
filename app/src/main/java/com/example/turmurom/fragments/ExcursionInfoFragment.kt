package com.example.turmurom.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentExcursionInfoBinding


class ExcursionInfoFragment : Fragment() {
    private lateinit var binding: FragmentExcursionInfoBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExcursionInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.excursion.observe(viewLifecycleOwner) {
            binding.tvExcursionDuration.text = it.duration
            binding.tvExcursionPrice.text = it.price
        }

        mainViewModel.guide.observe(viewLifecycleOwner){
            binding.tvFIO.text =
                it.lastName + " " + it.firstName + " " + it.patronymic
            binding.tvTelNumber.text = it.telNumber
            binding.tvEmail.text = it.eMail
            binding.ivGuidePhoto.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + it.pathPhoto))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExcursionInfoFragment()
    }
}