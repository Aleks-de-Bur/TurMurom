package com.example.turmurom.fragments

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Color
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.SearchAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentSearchBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.internal.ViewUtils.hideKeyboard


class SearchFragment : BottomSheetDialogFragment() {
//    private lateinit var binding: FragmentSearchBinding
//    private val adapter: SearchAdapter by lazy { SearchAdapter() }
//
//    private val mainViewModel: MainViewModel by activityViewModels {
//        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentSearchBinding.inflate(inflater, container, false)
//        return binding.root
//    }













//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//       //val titles = arrayOf("Памятник 1", "Памятник 2", "Достопримечательность", "Церковь")
//
////        val adapter : ArrayAdapter<String> = ArrayAdapter(
////            context?.applicationContext as MainApp, android.R.layout.simple_list_item_1,
////            titles
////        )
//
////        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener){
////            ove
////        }
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

//        binding.rcView.layoutManager = LinearLayoutManager(activity)
//        binding.rcView.adapter = SearchAdapter()
//
//        mainViewModel.readData.observe(viewLifecycleOwner, {
//            adapter.setData(it)
//        })
//
//            binding.searchView.isSubmitButtonEnabled = true


//        val countrySearch = binding.rcViewSearch
//
//        val searchIcon = countrySearch.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
//        searchIcon.setColorFilter(Color.WHITE)
//
//        val cancelIcon = countrySearch.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
//        cancelIcon.setColorFilter(Color.WHITE)
//
//        val textView = countrySearch.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
//        textView.setTextColor(Color.WHITE)


//        countrySearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
//                return false
//            }
//
//        })







            //binding.searchView.setOnQueryTextListener
    //}

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

//    private fun searchDatabase(query: String){
//        val searchQuery = "%$query%"
//
//        mainViewModel.searchMark(searchQuery).observe(this, { list ->
//            list.let {
//                adapter.setData(it)
//            }
//        })
//    }
//
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        if(query != null){
//            searchDatabase(query)
//        }
//        return true
//    }
//
//    override fun onQueryTextChange(query: String?): Boolean {
//        if(query != null){
//            searchDatabase(query)
//        }
//        return true
//    }

//    fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        activity?.menuInflater?.inflate(R.menu.search, menu)
//
//        val search = menu?.findItem(R.id.search)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//
//        return true
//    }
}