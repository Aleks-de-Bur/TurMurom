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
import com.example.turmurom.database.models.ExcursionPhoto
import com.example.turmurom.databinding.FragmentExcursionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExcursionFragment : Fragment(), ExcursionAdapter.ExcursionListener {
    private lateinit var binding: FragmentExcursionBinding
    private lateinit var adapter: ExcursionAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExcursionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcViewExcursion.layoutManager = LinearLayoutManager(activity)
        adapter = ExcursionAdapter(this@ExcursionFragment)
        rcViewExcursion.adapter = adapter
    }

    private fun observer(){
        mainViewModel.getAllExcursionsWithPhotos()
        mainViewModel.allExcursionsWithPhotos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mainViewModel.getAllExcursionsWithPhotos()
//        mainViewModel.allExcursionsWithPhotos.observe(this, {
//            it
//        })
//    }

    companion object{
        @JvmStatic
        fun newInstance() = CatalogFragment
    }

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

    override fun getPhoto(excursion: Excursion) : String {
        var ph = ""
        var photo : ExcursionPhoto? = null
        lifecycleScope.launch(Dispatchers.IO) {
            photo = mainViewModel.selectExcursionPhoto(excursion.id!!)
            //mainViewModel.excursionPhoto.value = photo


//            activity?.runOnUiThread() {
//                mainViewModel.excursionPhoto.value = photo
//                ph = photo!!.pathPhoto
//            }
        }

        mainViewModel.excursionPhoto.observe(viewLifecycleOwner){
            photo = it
        }

//        lifecycleScope.launch(Dispatchers.IO) {
//            mainViewModel.selectExcursionPhoto(excursion.id!!)
//        }

//        mainViewModel.markPhoto.observe(viewLifecycleOwner){
//            ph = it.pathPhoto
//        }

        return photo?.pathPhoto ?: ""
    }
}