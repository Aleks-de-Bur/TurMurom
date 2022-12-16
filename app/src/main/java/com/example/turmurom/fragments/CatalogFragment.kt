package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.CatalogAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.database.models.Mark
import com.example.turmurom.databinding.FragmentCatalogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogFragment : Fragment(), CatalogAdapter.CatalogListener {
    private lateinit var binding: FragmentCatalogBinding
    private lateinit var adapter: CatalogAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
        //mainViewModel.getCurrentCategories()
        binding.imageButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.categoryFilterFragment)
        }
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = CatalogAdapter(this@CatalogFragment)
        rcView.adapter = adapter
    }

    private fun observer() {
            mainViewModel.allMarksByCategory.observe(viewLifecycleOwner, {
                adapter.submitList(it)
            })

//        if (mainViewModel.filter) {
//            mainViewModel.allMarksByCategory.observe(viewLifecycleOwner, {
//                adapter.submitList(it)
//            })
//        } else {
//            mainViewModel.allMarks.observe(viewLifecycleOwner, {
//                adapter.submitList(it)
//            })
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.allMarks.observe(this, {
            it
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CatalogFragment
    }

    override fun onClick(mark: Mark) {
        mainViewModel.markId.value = mark
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectMarkPhotosById(mark.id!!)
        }
        Navigation.findNavController(requireView()).navigate(R.id.catalogItemFragment)
    }

    override fun onClickSchedule(mark: Mark) {
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectScheduleForMark(mark.id!!)
        }
        Navigation.findNavController(requireView()).navigate(R.id.scheduleFragment)
    }

    override fun onClickSearch() {
        Navigation.findNavController(requireView()).navigate(R.id.searchFragment)
    }

    override fun getPhoto(mark: Mark): String {
        lifecycleScope.launch(Dispatchers.IO) {
            val photo = mainViewModel.selectMarkPhoto(mark.id!!)
            activity?.runOnUiThread() {
                mainViewModel.markPhoto.value = photo
            }
        }
        return mainViewModel.markPhoto.value!!.pathPhoto
    }

    override fun getCategory(id: Int): String {
        return mainViewModel.dictOfCategories[id]!!
    }
}