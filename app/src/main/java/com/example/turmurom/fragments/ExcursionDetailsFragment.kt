package com.example.turmurom.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.turmurom.adapters.PhotoVPAdapter
import com.example.turmurom.adapters.VpAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.ExcursionPhoto
import com.example.turmurom.databinding.FragmentExcursionDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class ExcursionDetailsFragment : Fragment() {
    private lateinit var binding: FragmentExcursionDetailsBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    private val fragList = listOf(
        ExcursionDescriptionFragment.newInstance(),
        ExcursionInfoFragment.newInstance()
    )
    private val fragListTitles = listOf(
        "Описание",
        "Информация"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExcursionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = VpAdapter(activity as AppCompatActivity, fragList)
        binding.vp2.adapter = adapter
        TabLayoutMediator(binding.tb, binding.vp2){
            tab, pos -> tab.text = fragListTitles[pos]
        }.attach()


        binding.vpPhotos.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        var listPhotosName: MutableList<String> = mutableListOf()
        mainViewModel.allExcursionPhotosById.forEach {
            listPhotosName.add(it.pathPhoto)
        }

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
        fun newInstance() = ExcursionDetailsFragment()
    }
}