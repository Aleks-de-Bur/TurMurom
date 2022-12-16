package com.example.turmurom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.turmurom.R
import com.example.turmurom.activities.MainApp
import com.example.turmurom.database.MainViewModel
import com.example.turmurom.database.models.RegisterEntity
import com.example.turmurom.databinding.FragmentCatalogBinding
import com.example.turmurom.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            if (binding.tbLastName.text.toString() != "" &&
                binding.tbFirstName.text.toString() != "" &&
                binding.tbLogin.text.toString() != "" &&
                binding.tbEmail.text.toString() != "" &&
                binding.tbPassword.text.toString() != "" &&
                binding.tbRetryPassword.text.toString() != ""
            ) {
                if (!binding.tbEmail.text.toString().isValidEmail()) {
                    Toast.makeText(this.context, getString(R.string.eMailStringError), Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
                if (binding.tbPassword.text.toString() == binding.tbRetryPassword.text.toString()) {
                    val newUser = RegisterEntity(
                        null,
                        binding.tbLastName.text.toString(),
                        binding.tbFirstName.text.toString(),
                        binding.tbLogin.text.toString(),
                        binding.tbEmail.text.toString(),
                        binding.tbPassword.text.toString(),
                        null
                    )
                    mainViewModel.insertEntity(newUser)
                    Toast.makeText(this.context, "Регистрация прошла успешно!", Toast.LENGTH_LONG).show()
                    Navigation.createNavigateOnClickListener(R.id.btnLogIn,null)
                } else {
                    binding.tbRetryPassword.setText("")
                    Toast.makeText(this.context, getString(R.string.retryPasswordError), Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this.context, getString(R.string.signUpDataIsNull), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()

    }
}