package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    //private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMapBinding

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            mainViewModel.route.observe(viewLifecycleOwner){
                binding.tvRouteDescription.text = it.description
                binding.tvDuration.text = it.duration
                binding.tvTitle.text = it.name
            }
        } catch (e: Exception){

        }

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        checkPermission()
//    }
//
//    //Выводим сообщения в случае отсутствия разрешений
//    private fun permissionListener(){
//        pLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()){
//            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    //Проверяем разрешение местоположения
//    private fun checkPermission(){
//        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
//            permissionListener()
//            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()

    }
}