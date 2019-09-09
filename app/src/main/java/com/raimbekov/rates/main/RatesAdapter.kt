package com.raimbekov.rates.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raimbekov.rates.R
import com.raimbekov.rates.main.domain.model.Rate
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesAdapter() : RecyclerView.Adapter<ViewHolder>() {

    private var items: List<Rate> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.currencyTextView.text = items.get(position).currency
        holder.valueTextView.text = items.get(position).value.toString()
    }

    fun setItems(rates: List<Rate>) {
        items = rates
        notifyDataSetChanged()
    }
}

class ViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    val currencyTextView: TextView = view.currencyTextView
    val valueTextView: TextView = view.valueTextView
}