package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.RouteMarksAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentRouteMarksBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteMarksFragment : Fragment() {
    private lateinit var binding: FragmentRouteMarksBinding
    private lateinit var adapter: RouteMarksAdapter
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRouteMarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = RouteMarksAdapter()
        rcView.adapter = adapter
    }

    private fun observer(){
        adapter.submitList(mainViewModel.allMarksForRoute)

    }

    companion object {
        @JvmStatic
        fun newInstance() = RouteMarksFragment()
    }
}