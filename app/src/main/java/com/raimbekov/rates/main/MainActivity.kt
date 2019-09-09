package com.raimbekov.rates.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.raimbekov.rates.R
import com.raimbekov.rates.main.view.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter: RatesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.ratesLiveData.observe(this, Observer { rates ->
            adapter.setItems(rates)
        })

        viewModel.ratesError.observe(this, Observer {
            AlertDialog.Builder(this)
                .setMessage("Error loading rates")
                .show()
        })

        adapter = RatesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
