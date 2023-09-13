package com.jalper.myfirstapp.helloprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jalper.myfirstapp.R
import com.jalper.myfirstapp.databinding.ActivityHelloPrefsBinding
import com.jalper.myfirstapp.helloprefs.PreferenceKeys.NAME_KEY
import com.jalper.myfirstapp.helloprefs.PreferenceKeys.PREF_KEY
import com.jalper.myfirstapp.helloprefs.PreferenceKeys.PREF_PAGED_VIEWED_KEY
import com.jalper.myfirstapp.helloprefs.PreferenceKeys.PREF_TWO_KEY

class HelloPrefsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloPrefsBinding
    private var preferences: SharedPreferences? = null

    companion object{
        //const val PREF_KEY = "my_preferences"
        //const val NAME_KEY = "name_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloPrefsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        preferences = getSharedPreferences(PREF_TWO_KEY, Context.MODE_PRIVATE)

        binding.etHelloPrefsName.setText(
           preferences?.getString(NAME_KEY, "") ?: ""
        )

        binding.btnHelloPrefsName.setOnClickListener {
            val name = binding.etHelloPrefsName.text.toString()

//            if (name.isNotBlank())
//                preferences?.edit()
//                    ?.putString(NAME_KEY, name)
//                    ?.putBoolean(PREF_PAGED_VIEWED_KEY, true)
//                    ?.apply()

            val editor = preferences?.edit()

            editor?.putString(NAME_KEY, name)
            editor?.putBoolean(PREF_PAGED_VIEWED_KEY, true)
            editor?.apply()
        }

        preferences?.edit()?.putBoolean(PREF_PAGED_VIEWED_KEY, true)?.apply()
    }
}