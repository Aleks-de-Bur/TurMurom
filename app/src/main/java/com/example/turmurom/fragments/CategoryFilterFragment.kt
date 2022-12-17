package com.example.turmurom.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.CategoryFilterAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Category
import com.example.turmurom.databinding.CategoryListItemBinding
import com.example.turmurom.databinding.FragmentCategoryFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryFilterFragment : BottomSheetDialogFragment(),
    CategoryFilterAdapter.CategoryFilterListener {

    private lateinit var binding: FragmentCategoryFilterBinding
    private lateinit var adapter: CategoryFilterAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    val currentCategories: MutableMap<Int, Boolean> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryFilterBinding.inflate(inflater, container, false)
        for (cat in mainViewModel.currentCategories) {
            currentCategories[cat.key] = cat.value
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
        binding.btnSubmit.setOnClickListener {
            onSubmit()
        }
        binding.btnClear.setOnClickListener {
            onClear()
            initRcView()
            observer()
        }
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = CategoryFilterAdapter(this@CategoryFilterFragment)
        rcView.adapter = adapter
    }

    private fun observer() {
        mainViewModel.allCategories.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.allCategories.observe(this, {
            it
        })
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(category: Category, binding: CategoryListItemBinding) {

        currentCategories[category.id!!] = !currentCategories[category.id!!]!!
        coloringCategoryItems(category, binding)

    }

    override fun coloringCategoryItems(category: Category, binding: CategoryListItemBinding) {
        if (currentCategories[category.id!!] == true)
            binding.layout.setBackgroundColor(resources.getColor(R.color.currentCategory))
        else
            binding.layout.setBackgroundColor(resources.getColor(R.color.transparent))
    }

    private fun onClear() {
        for (category in currentCategories) {
            currentCategories[category.key] = false
        }
    }

    private fun onSubmit() {
        for (key in currentCategories.keys) {
            mainViewModel.currentCategories[key] = currentCategories[key]!!
        }
        mainViewModel.filterMarksByCategory()
        mainViewModel.updateChosenCategories()
        //Toast.makeText(context, "", Toast.LENGTH_LONG).show()

        dismiss()
    }


    companion object {
        @JvmStatic
        fun newInstance() = CategoryFilterFragment()
    }
}