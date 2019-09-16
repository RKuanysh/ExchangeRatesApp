package com.raimbekov.rates.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.raimbekov.rates.R
import kotlinx.android.synthetic.main.fragment_rates.*
import org.koin.android.viewmodel.ext.android.viewModel

class RatesFragment : Fragment() {

    private val viewModel by viewModel<RatesViewModel>()
    private lateinit var adapter: RatesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_rates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.ratesLiveData.observe(viewLifecycleOwner, Observer { ratesUpdateData ->
            adapter.setItems(ratesUpdateData.rates)
            if (ratesUpdateData.isFirstChanged) {
                adapter.notifyDataSetChanged()
            } else {
                adapter.notifyItemRangeChanged(1, ratesUpdateData.rates.size - 1)
            }
        })

        viewModel.ratesError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view.findViewById(R.id.rootView), R.string.error_loading_rates, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        viewModel.retry()
                    }
                })
                .show()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.isVisible = it
        })

        adapter = RatesAdapter(
            { viewModel.setCurrency(it) },
            { viewModel.setAmount(it) }
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}