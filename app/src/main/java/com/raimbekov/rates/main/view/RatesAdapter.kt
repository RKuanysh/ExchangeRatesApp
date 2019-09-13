package com.raimbekov.rates.main.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raimbekov.rates.R
import com.raimbekov.rates.main.domain.model.Rate
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesAdapter(
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var items: List<Rate> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            Log.i(javaClass.name, "Binding first")
        }
        holder.bind(items.get(position), position == 0)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

    }

    fun setItems(rates: List<Rate>) {
        items = rates
    }

    fun getItem(index: Int): Rate = items.get(index)
}

class ViewHolder(
    private val view: View,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val currencyTextView: TextView = view.currencyTextView
    private val valueEditText: TextView = view.valueEditText

    fun bind(rate: Rate, isEditable: Boolean) {
        currencyTextView.text = rate.currency
        valueEditText.text = rate.value.toString()
        valueEditText.isEnabled = isEditable

        view.setOnClickListener {
            onItemClickListener(adapterPosition)
        }
    }
}