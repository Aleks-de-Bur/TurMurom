package com.example.turmurom.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    lateinit var mapview: MapView
    lateinit var mapKit: MapKit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        permissionListener()
        checkPermission()

        mapview = binding.mapview
        mapview.map.move(
            CameraPosition(Point(55.579174, 42.052411), 15.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH,7f), null)
        //requestLocationPermission()
        mapKit = MapKitFactory.getInstance()
        var locationOnMapKit = mapKit.createUserLocationLayer(mapview.mapWindow)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLocation.setOnClickListener{
            var selfLocation = mapKit.createUserLocationLayer(mapview.mapWindow)
            selfLocation.isVisible = true
        }


    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        checkPermission()
//    }
//


    //Проверяем разрешение местоположения
    private fun checkPermission(){
        when{
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED -> {
                        Toast.makeText(context, "Разрешение на определение локации получено", Toast.LENGTH_LONG).show()
                    }
            else -> {
                pLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    //Выводим сообщения в случае отсутствия разрешений
    private fun permissionListener(){
        pLauncher = registerForActivityResult(

            ActivityResultContracts.RequestMultiplePermissions()){
            if(it[Manifest.permission.ACCESS_FINE_LOCATION] == true)
                Toast.makeText(requireContext(), "Locate run", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(requireContext(), "Нет доступа к местоположению", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()

    }
}