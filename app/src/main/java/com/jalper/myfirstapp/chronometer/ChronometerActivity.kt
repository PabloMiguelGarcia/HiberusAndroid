package com.jalper.myfirstapp.chronometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jalper.myfirstapp.R
import com.jalper.myfirstapp.databinding.ActivityChronometerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChronometerActivity : AppCompatActivity() {
    private val binding: ActivityChronometerBinding by lazy {
        ActivityChronometerBinding.inflate(layoutInflater)
    }

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning: Boolean = false
    private var resetTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabActivityChronometerStart.setOnClickListener {
            start()
        }

        binding.fabActivityChronometerStop.setOnClickListener {
            stop()
        }

        binding.fabActivityChronometerReset.setOnClickListener {
            reset()
        }
    }

    private fun start(){
        if (!isRunning){
            isRunning = true

            var difTime:Long = 0 // Variable para restar el error de tiempo al darle stop

            if (resetTime)
                startTime = System.currentTimeMillis()
            else
                difTime = System.currentTimeMillis() - startTime - elapsedTime //10 //0 //2

            lifecycleScope.launch(Dispatchers.Main) {
                while(isRunning){
                    elapsedTime = System.currentTimeMillis() - startTime - difTime
                    binding.tvActivityChronometerTime.text = elapsedTime.toTimeString()
                    delay(1000)
                }
            }
        }
    }

    private fun stop(){
        isRunning = false
        resetTime = false
    }

    private fun reset(){
        isRunning = false
        resetTime = true
        binding.tvActivityChronometerTime.text = "00:00:00"
    }

    private fun Long.toTimeString(): String{
        val totalSeconds = this / 1000

        val seconds = totalSeconds % 60 // Resto dividirlo por 60, resto de minutos
        val minutes = (totalSeconds / 60) % 60
        val hours = totalSeconds / 60 / 60

        val hoursString = if (hours < 10) "0$hours" else hours.toString()
        val minutesString = if (minutes < 10) "0$minutes" else minutes.toString()
        val secondsString = if (seconds < 10) "0$seconds" else seconds.toString()

        return "$hoursString:$minutesString:$secondsString"
    }
}