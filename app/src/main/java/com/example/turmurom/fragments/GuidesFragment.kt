package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.ExcursionAdapter
import com.example.turmurom.adapters.GuideAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.database.models.Guide
import com.example.turmurom.databinding.FragmentExcursionBinding
import com.example.turmurom.databinding.FragmentGuidesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuidesFragment : Fragment(), GuideAdapter.GuideListener {
    private lateinit var binding: FragmentGuidesBinding
    private lateinit var adapter: GuideAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcViewGuide.layoutManager = LinearLayoutManager(activity)
        adapter = GuideAdapter(this@GuidesFragment)
        rcViewGuide.adapter = adapter
    }

    private fun observer(){
        mainViewModel.allGuides.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.allGuides.observe(this, {
            it
        })
    }

    override fun onClick(guide: Guide) {
//        lifecycleScope.launch(Dispatchers.IO) {
            //mainViewModel.getExcursionsForGuide(guide.id!!)
//        }
        mainViewModel.guideId = guide.id!!
        Navigation.findNavController(requireView()).navigate(R.id.guideDetailsFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GuidesFragment()
    }
}