package com.example.turmurom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.ScheduleAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentScheduleBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScheduleFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var adapter: ScheduleAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ScheduleAdapter()
        rcView.adapter = adapter
    }

    private fun observer(){
        mainViewModel.allSchedule.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.allSchedule.observe(this, {
            it
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ScheduleFragment()
    }
}