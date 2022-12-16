package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.adapters.ExcursionAdapter
import com.example.turmurom.adapters.RouteAdapter
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.Mark
import com.example.turmurom.database.models.Route
import com.example.turmurom.databinding.FragmentRoutesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutesFragment : Fragment(), RouteAdapter.RouteListener {
    private lateinit var binding: FragmentRoutesBinding
    private lateinit var adapter: RouteAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcViewRoutes.layoutManager = LinearLayoutManager(activity)
        adapter = RouteAdapter(this@RoutesFragment)
        rcViewRoutes.adapter = adapter
    }

    private fun observer(){
        mainViewModel.allRoutes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.allRoutes.observe(this, {
            it
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = RoutesFragment()
    }

    override fun onClick(route: Route) {
        mainViewModel.route.value = route

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectRouteMarksById(route.id!!)
        }

        Navigation.findNavController(requireView()).navigate(R.id.routeDetailsFragment)
    }

    override fun onGo(route: Route) {
        mainViewModel.route.value = route

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.selectRouteMarksById(route.id!!)
        }

        findNavController().navigate(R.id.mapMenuItem)
        //Navigation.findNavController(requireView()).navigate(R.id.mapMenuItem)

        //Navigation.createNavigateOnClickListener(R.id.mapMenuItem, null)
    }
}