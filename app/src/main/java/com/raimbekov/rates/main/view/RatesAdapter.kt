package com.raimbekov.rates.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raimbekov.rates.R
import com.raimbekov.rates.main.domain.model.Rate
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesAdapter(
    private val onItemClickListener: (Rate) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var items: List<Rate> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    fun setItems(rates: List<Rate>) {
        items = rates
        notifyDataSetChanged()
    }
}

class ViewHolder(
    private val view: View,
    private val onItemClickListener: (Rate) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val currencyTextView: TextView = view.currencyTextView
    private val valueTextView: TextView = view.valueTextView

    fun bind(rate: Rate) {
        currencyTextView.text = rate.currency
        valueTextView.text = rate.value.toString()

        view.setOnClickListener {
            onItemClickListener(rate)
        }
    }
}