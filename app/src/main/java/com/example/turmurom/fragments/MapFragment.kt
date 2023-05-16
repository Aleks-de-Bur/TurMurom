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
import com.example.turmurom.R
import com.example.turmurom.activities.MainActivity
import com.example.turmurom.databinding.FragmentMapBinding
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.HttpOptions
import ru.dgis.sdk.KeyFromString
import ru.dgis.sdk.KeySource
import ru.dgis.sdk.LogLevel
import ru.dgis.sdk.LogOptions
import ru.dgis.sdk.PersonalDataCollectionConsent
import ru.dgis.sdk.VendorConfig
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.imageFromResource

class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

    //lateinit var mapview: MapView
    //lateinit var mapKit: MapKit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройки журналирования
        val logOptions = LogOptions(
            LogLevel.VERBOSE
        )

        // Настройки HTTP-клиента
        val httpOptions = HttpOptions(
            useCache = false
        )

        // Согласие на сбор и отправку персональных данных
        val dataCollectConsent = PersonalDataCollectionConsent.GRANTED

        val sdkContext = DGis.initialize(
            appContext = requireActivity().applicationContext,
            dataCollectConsent = dataCollectConsent,
            logOptions = logOptions,
            httpOptions = httpOptions,
            vendorConfig = VendorConfig(),
            keySource = KeySource(KeyFromString(""))
        )

        val mapView = binding.mapView
        lifecycle.addObserver(mapView)

        mapView.getMapAsync { map ->
            // Действия с картой
            val camera = map.camera
            val mapObjectManager = MapObjectManager(map)

            val icon = imageFromResource(sdkContext, R.drawable.ic_marker)

            val marker = Marker(
                MarkerOptions(
                    position = GeoPointWithElevation(
                        latitude = 55.752425,
                        longitude = 37.613983
                    ),
                    icon = icon
                )
            )

            mapObjectManager.addObject(marker)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

//        // Настройки журналирования
//        val logOptions = LogOptions(
//            LogLevel.VERBOSE
//        )
//
//        // Настройки HTTP-клиента
//        val httpOptions = HttpOptions(
//            useCache = false
//        )
//
//        // Согласие на сбор и отправку персональных данных
//        val dataCollectConsent = PersonalDataCollectionConsent.GRANTED
//


//        val mapView = binding.mapView
//        lifecycle.addObserver(mapView)
//
//        mapView.getMapAsync { map ->
//            // Действия с картой
//            val camera = map.camera
//            val mapObjectManager = MapObjectManager(map)
//
//            val icon = imageFromResource(sdkContext, R.drawable.ic_marker)
//
//            val marker = Marker(
//                MarkerOptions(
//                    position = GeoPointWithElevation(
//                        latitude = 55.752425,
//                        longitude = 37.613983
//                    ),
//                    icon = icon
//                )
//            )
//
//            mapObjectManager.addObject(marker)
//        }









//        permissionListener()
//        checkPermission()
//
//        mapview = binding.mapview
//        mapview.map.move(
//            CameraPosition(Point(55.579174, 42.052411), 15.0f, 0.0f, 0.0f),
//            Animation(Animation.Type.SMOOTH,7f), null)
//        //requestLocationPermission()
//        mapKit = MapKitFactory.getInstance()
//        var locationOnMapKit = mapKit.createUserLocationLayer(mapview.mapWindow)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnLocation.setOnClickListener{
//            var selfLocation = mapKit.createUserLocationLayer(mapview.mapWindow)
//            selfLocation.isVisible = true
//        }


    }


    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()

    }
}