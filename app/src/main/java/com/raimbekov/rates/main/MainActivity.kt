package com.raimbekov.rates.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raimbekov.rates.main.view.RatesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, RatesFragment())
                .commit()
        }
    }
}
