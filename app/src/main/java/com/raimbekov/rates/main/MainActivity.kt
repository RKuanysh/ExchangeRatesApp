package com.raimbekov.rates.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.raimbekov.rates.R
import com.raimbekov.rates.main.view.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.ratesLiveData.observe(this, Observer { rates ->
            Toast.makeText(this, "received ${rates.size} rates", Toast.LENGTH_SHORT).show()
        })

        viewModel.ratesError.observe(this, Observer {
            AlertDialog.Builder(this)
                .setMessage("Error loading rates")
                .show()
        })
    }
}
