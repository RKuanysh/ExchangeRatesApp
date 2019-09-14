package com.raimbekov.rates.main.view

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raimbekov.rates.R
import com.raimbekov.rates.main.domain.model.Rate
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesAdapter(
    private val onItemClickListener: (Int) -> Unit,
    private val onValueChangedListener: (Double) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var items: List<Rate> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
        return ViewHolder(view, onItemClickListener, onValueChangedListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position), position == 0)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    fun setItems(rates: List<Rate>) {
        items = rates
    }

    fun getItem(index: Int): Rate = items.get(index)
}

class ViewHolder(
    private val view: View,
    private val onItemClickListener: (Int) -> Unit,
    private val onValueChangedListener: (Double) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val currencyTextView: TextView = view.currencyTextView
    private val valueEditText: EditText = view.valueEditText
    private val valueChangeListener: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            onValueChangedListener(s.toString().toDoubleOrNull() ?: 0.0)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    fun bind(rate: Rate, isEditable: Boolean) {
        currencyTextView.text = rate.currency
        valueEditText.setText(rate.value.toString())

        if (isEditable) {
            valueEditText.setSelection(valueEditText.text.length)
            valueEditText.addTextChangedListener(valueChangeListener)
        }

        view.setOnClickListener {
            onItemClickListener(adapterPosition)
        }
        valueEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                onItemClickListener(adapterPosition)
            }
        }
    }

    fun unbind() {
        valueEditText.removeTextChangedListener(valueChangeListener)
        valueEditText.setOnFocusChangeListener(null)
    }
}