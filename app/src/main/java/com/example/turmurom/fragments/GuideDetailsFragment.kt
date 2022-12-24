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
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.databinding.FragmentExcursionBinding
import com.example.turmurom.databinding.FragmentGuideDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuideDetailsFragment : Fragment(), ExcursionAdapter.ExcursionListener {
    private lateinit var binding: FragmentGuideDetailsBinding
    private lateinit var adapter: ExcursionAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuideDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcViewExcursions.layoutManager = LinearLayoutManager(activity)
        adapter = ExcursionAdapter(this@GuideDetailsFragment)
        rcViewExcursions.adapter = adapter
    }

    private fun observer(){
//        lifecycleScope.launch(Dispatchers.IO) {
//            mainViewModel.getExcursionsForGuide(mainViewModel.guideId)
//        }
//        mainViewModel.allExcursionsForGuide.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }

        mainViewModel.getAllExcursionsForGuide(mainViewModel.guideId)
        mainViewModel.allExcursionsForGuide.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mainViewModel.allExcursionsForGuide.observe(this, {
//            it
//        })
//    }

    override fun onClick(excursion: Excursion) {
        mainViewModel.excursion.value = excursion
        lifecycleScope.launch(Dispatchers.IO) {
            val gu = mainViewModel.selectGuide(excursion.guideId)
            mainViewModel.selectExcursionPhotosById(excursion.id!!)         //Фотографии

            activity?.runOnUiThread() {
                mainViewModel.guide.value = gu
            }
        }
        Navigation.findNavController(requireView()).navigate(R.id.excursionDetailsFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GuideDetailsFragment()
    }
}