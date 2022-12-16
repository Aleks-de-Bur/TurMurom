package com.example.turmurom.fragments

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.turmurom.activities.AuthorizationActivity
import com.example.turmurom.activities.MainActivity
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.databinding.FragmentPersonalAccountBinding
import com.example.turmurom.preference.SharedPreference

class PersonalAccountFragment : Fragment() {
    private lateinit var binding: FragmentPersonalAccountBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val curUser = mainViewModel.currentUser
        binding.tvLogin.text = curUser.login
        binding.tvEMail.text = curUser.email
        //binding.ivAvatar.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/main_photo"))
        if (curUser.photo == null || curUser.photo == "") {
            binding.ivAvatar.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/main_photo"))
        } else {
            binding.ivAvatar.setImageURI(Uri.parse("android.resource://com.example.turmurom/raw/" + curUser.photo))
        }

        binding.btnLogOut.setOnClickListener{
            val sharedPreference: SharedPreference = SharedPreference(requireContext())
            sharedPreference.removeValue("userId")
            activity?.finish()
            startActivity(Intent(activity, AuthorizationActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PersonalAccountFragment()
    }
}