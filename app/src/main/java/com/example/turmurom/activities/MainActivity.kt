package com.example.turmurom.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.turmurom.R
import com.example.turmurom.database.MainDb
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.ActivityMainBinding
import com.example.turmurom.preference.SharedPreference
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var conf: AppBarConfiguration
    private lateinit var navController: NavController

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }
    //lateinit var mapview: MapView
    //lateinit var selfLocation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference = SharedPreference(this)


//        lifecycleScope.launch(Dispatchers.Default) {
////            mainViewModel.selectUserById(sharedPreference.getValueInt("userId"))
//            mainViewModel.selectUserById(1)
//        }

        mainViewModel.selectUserById(1)

        //Toast.makeText(this, mainViewModel.currentUser.login, Toast.LENGTH_SHORT).show()

        MapKitFactory.setApiKey("a44ceb31-ee98-4267-bcc7-294cda8c8cc3");
        MapKitFactory.initialize(this);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.actionBar.toolbar)

        navController = findNavController(R.id.fragmentContainerView)
        conf = AppBarConfiguration(
            setOf(
                R.id.mapMenuItem,
                R.id.guidesMenuItem,
                R.id.excursionsMenuItem,
                R.id.catalogMenuItem,
                R.id.routesMenuItem,
                R.id.profileMenuItem,
                R.id.btnSignUp,
                R.id.btnLogIn
            ), binding.drawer
        )
        setupActionBarWithNavController(navController, conf)
        binding.navView.setupWithNavController(navController)

        //Подключение к базе данных
        //val db = MainDb.getDB(this)

//        selfLocation = binding.btnLocation
//        mapview = binding.mapview
//        mapview.map.move(CameraPosition(Point(55.579174, 42.052411), 11.0f, 0.0f, 0.0f),
//        Animation(Animation.Type.SMOOTH,7f), null)
//        requestLocationPermission()
//        var mapKit: MapKit = MapKitFactory.getInstance()
//        var locationOnMapKit = mapKit.createUserLocationLayer(mapview.mapWindow)
//
//        // Проверить!!!!!!!!!
//        selfLocation.setOnClickListener {
//            locationOnMapKit.isVisible = !locationOnMapKit.isVisible
//        }






        mainViewModel.allCategories.observe(this){
            it.forEach{
                mainViewModel.addCategory(it.id!!, it.title)
            }
            mainViewModel.getCurrentCategories()


            var id = ""
            for (category in mainViewModel.currentCategories) {
                if (mainViewModel.currentCategories[category.key] == true)
                    id += category.key
            }

            lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.getListOfMarksByCategory(id.trim(','))
            }


        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(conf) || super.onSupportNavigateUp()
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            return
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //adapter.filter.filter(newText)
        return true
    }


//    override fun onStop() {
//        mapview.onStop()
//        MapKitFactory.getInstance().onStop()
//        super.onStop()
//    }
//
//    override fun onStart() {
//        mapview.onStart()
//        MapKitFactory.getInstance().onStart()
//        super.onStart()
//    }
}