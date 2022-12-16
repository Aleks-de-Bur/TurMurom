package com.example.turmurom.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.turmurom.R
import com.example.turmurom.activities.MainActivity
import com.example.turmurom.database.MainDb
import com.example.turmurom.databinding.FragmentLogInBinding
import com.example.turmurom.preference.SharedPreference
import com.example.turmurom.repositories.RegisterRepository

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private lateinit var loginViewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        //val binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)

        val application = requireNotNull(this.activity).application

        val dao = MainDb.getDB(application).getRegisterEntityDao()

        val repository = RegisterRepository(dao)

        val factory = LogInViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LogInViewModel::class.java)

        //binding.myLogInViewModel = loginViewModel

        //binding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                displayUsersList()
                loginViewModel.doneNavigatingRegiter()
            }
        })

        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername .observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigatetoUserDetails.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateUserDetails()
                loginViewModel.doneNavigatingUserDetails()
            }
        })

        //binding.btnSignUp.findNavController().navigate(R.id.registerFragment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener{
            loginViewModel.signUP()
        }
        binding.btnLogIn.setOnClickListener{
            loginViewModel.loginButton()
        }

        binding.tbEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                loginViewModel.inputUsername.value = binding.tbEmail.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        binding.tbPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                loginViewModel.inputPassword.value = binding.tbPassword.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun displayUsersList() {
        Log.i("MYTAG","insidisplayUsersList")
        Navigation.findNavController(requireView()).navigate(R.id.registerFragment)

    }

    private fun navigateUserDetails() {
        Log.i("MYTAG","insidisplayUsersList")

        val sharedPreference = SharedPreference(requireContext())

        loginViewModel.userId.observe(viewLifecycleOwner){id->
            sharedPreference.save("userId", id)
        }



//        val editor = sharedPreference.edit()
//        editor?.putString("EMAIL", loginViewModel.inputUsername.value)
//        editor?.putString("PASSWORD", loginViewModel.inputPassword.value)
//        editor?.apply()

        //Navigation.findNavController(requireView()).navigate(R.id.userDetailsFragment)
        val intent = Intent(context, MainActivity::class.java)
        activity?.finish()
        startActivity(intent)

    }
}