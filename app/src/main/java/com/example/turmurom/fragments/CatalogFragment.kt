package com.example.turmurom.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.CatalogAdapter
import com.example.turmurom.adapters.CategoryFilterAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.*
import com.example.turmurom.databinding.CategoryListItemBinding
import com.example.turmurom.databinding.FragmentCatalogBinding
import com.example.turmurom.databinding.MarkListItemBinding
import com.example.turmurom.preference.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogFragment : Fragment(), CatalogAdapter.CatalogListener,
    CategoryFilterAdapter.CategoryFilterListener {
    private lateinit var binding: FragmentCatalogBinding
    private lateinit var catalogAdapter: CatalogAdapter
    private val catalogFilterAdapter: CategoryFilterAdapter = CategoryFilterAdapter(this)

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
        initRcViewCategories()
        observer()
        binding.imageButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.categoryFilterFragment)
        }
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        catalogAdapter = CatalogAdapter(this@CatalogFragment)
        rcView.adapter = catalogAdapter
    }

    /**
     * В горизонтальный ресайклер пихаем адаптер по фильтрам
     */
    private fun initRcViewCategories() {
        binding.rcViewCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = catalogFilterAdapter
        }
    }

    private fun observer() {
        val sharedPreference = SharedPreference(requireContext())
        mainViewModel.filterMarksByCategory(sharedPreference.getValueInt("userId"))
        mainViewModel.allMarksByCategory.observe(viewLifecycleOwner) {
            catalogAdapter.submitList(it)
        }
        mainViewModel.chosenCategoriesLiveData.observe(
            viewLifecycleOwner,
            catalogFilterAdapter::submitList
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = CatalogFragment
    }

    override fun onClick(marksWithPhotos: MarksWithPhotos) {
        mainViewModel.markId.value = marksWithPhotos.mark
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectMarkPhotosById(marksWithPhotos.mark.id!!)
        }
        Navigation.findNavController(requireView()).navigate(R.id.catalogItemFragment)
    }

    override fun onClickSchedule(marksWithPhotos: MarksWithPhotos) {
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectScheduleForMark(marksWithPhotos.mark.id!!)
        }
        Navigation.findNavController(requireView()).navigate(R.id.scheduleFragment)
    }

    override fun onClickSearch() {
        Navigation.findNavController(requireView()).navigate(R.id.searchFragment)
    }

    override fun onClickElect(marksWithPhotos: MarksWithPhotos, binding: MarkListItemBinding) {
        val sharedPreference = SharedPreference(requireContext())

        if(marksWithPhotos.mark.elected) {
            binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elect_35"))
            mainViewModel.deleteElectedMark(marksWithPhotos.mark.id!!, sharedPreference.getValueInt("userId"))

        }
        else{
            binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elected_35"))
            mainViewModel.insertElectedMark(UserElected(null, sharedPreference.getValueInt("userId"), marksWithPhotos.mark.id!!))
        }
        mainViewModel.filterMarksByCategory(sharedPreference.getValueInt("userId"))

    }

    //    override fun getPhoto(mark: Mark): String {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val photo = mainViewModel.selectMarkPhoto(mark.id!!)
//            activity?.runOnUiThread() {
//                mainViewModel.markPhoto.value = photo
//            }
//        }
//        return mainViewModel.markPhoto.value!!.pathPhoto
//    }

    /**
     * Переопределение листенера при клике на фильтр
     */
    override fun onClick(category: Category, binding: CategoryListItemBinding) {
        val sharedPreference = SharedPreference(requireContext())
        mainViewModel.currentCategories[category.id!!] =
            !mainViewModel.currentCategories[category.id!!]!!
        mainViewModel.filterMarksByCategory(sharedPreference.getValueInt("userId"))
        mainViewModel.updateChosenCategories()
    }

    override fun coloringCategoryItems(category: Category, binding: CategoryListItemBinding) {
        if (mainViewModel.currentCategories[category.id!!] == true)
            binding.layout.setBackgroundColor(resources.getColor(R.color.currentCategory))
        else
            binding.layout.setBackgroundColor(resources.getColor(R.color.transparent))
    }

    override fun getCategory(id: Int): String {
        return mainViewModel.dictOfCategories[id]!!
    }
}