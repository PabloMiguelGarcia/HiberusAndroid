package com.jalper.myfirstapp.coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.jalper.myfirstapp.R
import com.jalper.myfirstapp.databinding.ActivityCoroutinesExampleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class CoroutinesExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutinesExampleBinding

    private val taskOneState = MutableLiveData<CoroutineResult<String>>()
    private val taskTwoState = MutableLiveData<CoroutineResult<Bitmap>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoroutinesExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnActivityCoroutinesExampleTask1.setOnClickListener {
            launchTaskOne()
        }
        
        binding.btnActivityCoroutinesExampleTask2.setOnClickListener { 
            launchTaskTwo()
        }

        taskOneState.observe(this){ result ->
            handleTaskOneState(result)
        }
        
        taskTwoState.observe(this) {result ->
            handleTaskTwoState(result)
        }
    }

    private fun handleTaskOneState(state: CoroutineResult<String>){
        when (state){
            is CoroutineResult.Loading -> {
                binding.pbActivityCoroutinesExampleProgressTasks.visibility = View.VISIBLE
            }
            is CoroutineResult.Success -> {
                binding.pbActivityCoroutinesExampleProgressTasks.visibility = View.GONE
                binding.tvActivityCoroutinesExampleResult.text = getString(R.string.coroutine_result, state.result)
            }
            else -> {
                binding.pbActivityCoroutinesExampleProgressTasks.visibility = View.GONE
            }
        }
    }

    private fun launchTaskOne(){
        taskOneState.value = CoroutineResult.Loading()

        lifecycleScope.launch(Dispatchers.IO) {

            val result = doTaskOne()

            withContext(Dispatchers.Main){
                taskOneState.value = CoroutineResult.Success(result)
            }
        }
    }

    private suspend fun doTaskOne(): String{
        delay(3000)
        return "My First Coroutine"
    }

    private fun handleTaskTwoState(state: CoroutineResult<Bitmap>) {
        when (state){
            is CoroutineResult.Loading -> {
                binding.pbActivityCoroutinesExampleProgressTasks.visibility = View.VISIBLE
            }
            is CoroutineResult.Success -> {
                binding.pbActivityCoroutinesExampleProgressTasks.visibility = View.GONE
                binding.ivActivityCoroutinesExampleImage.setImageBitmap(state.result)
            }
            else -> {
                binding.pbActivityCoroutinesExampleProgressTasks.visibility = View.GONE
            }
        }
    }

    private fun launchTaskTwo() {
        taskTwoState.value = CoroutineResult.Loading()

        lifecycleScope.launch(Dispatchers.IO) {

            val result = doTaskTwo("https://raulperez.tieneblog.net/wp-content/uploads/2015/09/tux-transparente.png")

            withContext(Dispatchers.Main){
                taskTwoState.value = CoroutineResult.Success(result)
            }
        }
    }

    private fun doTaskTwo(url: String): Bitmap {
        val responseStream = URL(url).openStream()

        return BitmapFactory.decodeStream(responseStream)
    }
}