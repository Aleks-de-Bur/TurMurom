package com.example.turmurom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.turmurom.databinding.FragmentExampleApiBinding
import com.example.turmurom.retrofit.ExampleAPI
import com.example.turmurom.retrofit.RetrofitService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level
import java.util.logging.Logger


class ExampleAPIFragment : Fragment() {
    private lateinit var binding: FragmentExampleApiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExampleApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val gson = GsonBuilder()
//            .setLenient()
//            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.75:8080/api/").client(client)
//            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val exampleApi = retrofit.create(ExampleAPI::class.java)

        binding.apiBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val category = exampleApi.getCategoryById(1)
                activity?.runOnUiThread{
                    binding.tvTitle.text = category.body()!!.title
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExampleAPIFragment
    }
}