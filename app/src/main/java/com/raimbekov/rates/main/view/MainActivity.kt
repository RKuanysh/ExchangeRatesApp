package com.raimbekov.rates.main.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.raimbekov.rates.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter: RatesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.ratesLiveData.observe(this, Observer { ratesUpdateData ->
            adapter.setItems(ratesUpdateData.rates)
            if (ratesUpdateData.isFirstChanged) {
                adapter.notifyDataSetChanged()
            } else {
                adapter.notifyItemRangeChanged(1, ratesUpdateData.rates.size - 1)
            }
        })

        viewModel.ratesError.observe(this, Observer {
            AlertDialog.Builder(this)
                .setMessage("Error loading rates")
                .show()
        })

        viewModel.loading.observe(this, Observer {
            progressBar.isVisible = it
        })

        adapter = RatesAdapter(
            { viewModel.setCurrency(it, adapter.getItem(it)) },
            { viewModel.setAmount(it) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
