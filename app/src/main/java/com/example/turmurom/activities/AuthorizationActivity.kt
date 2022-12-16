package com.example.turmurom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.turmurom.databinding.ActivityAuthorizationBinding
import com.example.turmurom.preference.SharedPreference

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference: SharedPreference = SharedPreference(this)

        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //navController = findNavController(R.id.fragmentContainerView)

        //Toast.makeText(this, sharedPreference.getValueInt("userId").toString(), Toast.LENGTH_SHORT).show()

        if (sharedPreference.getValueInt("userId") != 0) {

            finish()
            startActivity(Intent(this, MainActivity::class.java))

            //Toast.makeText(this, sharedPreference.getValueString("user"), Toast.LENGTH_SHORT).show()
        }
    }
}