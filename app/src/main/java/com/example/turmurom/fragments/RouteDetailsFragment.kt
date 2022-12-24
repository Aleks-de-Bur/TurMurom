package com.example.turmurom.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.VpAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentRouteDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRouteDetailsBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    private val fragList = listOf(
        RouteDescriptionFragment.newInstance(),
        RouteMarksFragment.newInstance()
    )
    private val fragListTitles = listOf(
        "Описание",
        "Путевые точки"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRouteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.allMarksForRoute.clear()
        mainViewModel.allRouteMarksById.forEach {
            lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.getListOfMarksForRoute(it.markId)
            }
        }

        val adapter = VpAdapter(activity as AppCompatActivity, fragList)
        binding.vp2.adapter = adapter
        TabLayoutMediator(binding.tb, binding.vp2){
                tab, pos -> tab.text = fragListTitles[pos]
        }.attach()

        mainViewModel.route.observe(viewLifecycleOwner){
            binding.ivRoute.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + it.pathPhoto))
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = RouteDetailsFragment()
    }
}