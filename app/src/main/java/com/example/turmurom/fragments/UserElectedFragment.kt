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
import androidx.recyclerview.widget.RecyclerView
import com.example.turmurom.R
import com.example.turmurom.activities.MainActivity
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.CategoryFilterAdapter
import com.example.turmurom.adapters.ElectedMarksAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Category
import com.example.turmurom.database.models.ElectedMarks
import com.example.turmurom.database.models.Mark
import com.example.turmurom.database.models.UserElected
import com.example.turmurom.databinding.CategoryListItemBinding
import com.example.turmurom.databinding.FragmentUserElectedBinding
import com.example.turmurom.databinding.MarkListItemBinding
import com.example.turmurom.preference.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.AccessController.getContext

class UserElectedFragment : Fragment(), ElectedMarksAdapter.CatalogListener,
    CategoryFilterAdapter.CategoryFilterListener {
    private lateinit var binding: FragmentUserElectedBinding
    private lateinit var electedMarksAdapter: ElectedMarksAdapter
    private val catalogFilterAdapter: CategoryFilterAdapter = CategoryFilterAdapter(this)


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserElectedBinding.inflate(inflater, container, false)
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
        electedMarksAdapter = ElectedMarksAdapter(this@UserElectedFragment)
        rcView.adapter = electedMarksAdapter
    }

    /**
     * В горизонтальный ресайклер пихаем адаптер по фильтрам
     */
    private fun initRcViewCategories() {
        binding.rcViewCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = catalogFilterAdapter
        }
    }

    private fun observer() {
        val sharedPreference: SharedPreference = SharedPreference(requireContext())
        mainViewModel.getElectedMarks(sharedPreference.getValueInt("userId"))
        mainViewModel.filterMarksByCategory()
        mainViewModel.electedMarks.observe(viewLifecycleOwner) {
            electedMarksAdapter.submitList(it)
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

    override fun onClick(electedMark: ElectedMarks) {            //Подумать над получением фото

        mainViewModel.markId.value = Mark(electedMark.id, electedMark.title, electedMark.description,
            electedMark.categoryId, electedMark.address)

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectMarkPhotosById(electedMark.id!!)
        }
        Navigation.findNavController(requireView()).navigate(R.id.catalogItemFragment)
    }

    override fun onClickSchedule(electedMark: ElectedMarks) {
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectScheduleForMark(electedMark.id!!)
        }
        Navigation.findNavController(requireView()).navigate(R.id.scheduleFragment)
    }

    override fun onClickElect(markId: Int) {
//        if(mainViewModel.flag)
//            binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elected_35"))
//        else
//            binding.ibElect.setImageURI(Uri.parse("android.resource://com.example.turmurom/drawable/elect_35"))


        val sharedPreference = SharedPreference(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.deleteElectedMark(markId, sharedPreference.getValueInt("userId"))
        }


        mainViewModel.getElectedMarks(sharedPreference.getValueInt("userId"))

    }

    /**
     * Переопределение листенера при клике на фильтр
     */
    override fun onClick(category: Category, binding: CategoryListItemBinding) {
        mainViewModel.currentCategories[category.id!!] =
            !mainViewModel.currentCategories[category.id!!]!!
        mainViewModel.filterMarksByCategory()
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