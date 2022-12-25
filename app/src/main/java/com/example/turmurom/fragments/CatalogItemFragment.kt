package com.example.turmurom.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.PhotoVPAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Mark
import com.example.turmurom.databinding.FragmentCatalogItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class CatalogItemFragment : Fragment() {
    private lateinit var binding: FragmentCatalogItemBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private var markItem: Mark? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.markId.observe(activity as LifecycleOwner, {
            markItem = it
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.description
            binding.tvAddress.text = it.address
        })

        binding.btnSchedule.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.selectScheduleForMark(markItem?.id!!)
            }
            findNavController().navigate(R.id.scheduleFragment)
        }

        binding.vpPhotos.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        var listPhotosName: MutableList<String> = mutableListOf()
        mainViewModel.allMarkPhotosById.forEach {
            listPhotosName.add(it.pathPhoto)
        }
        //binding.vpPhotos.adapter = PhotoVPAdapter(mainViewModel.allMarkPhotosById)
        binding.vpPhotos.adapter = PhotoVPAdapter(listPhotosName)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.vpPhotos.setPageTransformer(compositePageTransformer)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CatalogItemFragment()
    }
}