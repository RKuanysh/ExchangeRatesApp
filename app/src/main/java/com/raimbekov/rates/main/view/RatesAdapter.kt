package com.raimbekov.rates.main.view

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raimbekov.rates.R
import com.raimbekov.rates.main.view.model.CurrencyHolder
import com.raimbekov.rates.main.view.model.RateViewData
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesAdapter(
    private val onItemClickListener: (RateViewData) -> Unit,
    private val onValueChangedListener: (String) -> Unit,
    private val getBaseCurrency: () -> String
) : RecyclerView.Adapter<ViewHolder>() {

    private var items: List<RateViewData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
        return ViewHolder(view, getBaseCurrency, onItemClickListener, onValueChangedListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position), position == 0)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onValueChanged(items.get(position))
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    fun setItems(rates: List<RateViewData>) {
        items = rates
    }
}

class ViewHolder(
    private val view: View,
    private val getBaseCurrency: () -> String,
    private val onItemClickListener: (RateViewData) -> Unit,
    private val onValueChangedListener: (String) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val currencySymbolView: TextView = view.currencySymbolView
    private val currencyNameView: TextView = view.currencyNameTextView
    private val currencyImageView: ImageView = view.currencyImageView
    private val valueEditText: EditText = view.valueEditText
    private val valueChangeListener: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            onValueChangedListener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    fun bind(rate: RateViewData, isEditable: Boolean) {
        onValueChanged(rate)

        val currencyData = CurrencyHolder.getCurrency(rate.currency)

        currencySymbolView.setText(rate.currency)
        currencyNameView.setText(currencyData.nameResId)
        currencyImageView.setImageResource(currencyData.flagResId)


        if (isEditable) {
            valueEditText.setSelection(valueEditText.text.length)
            valueEditText.addTextChangedListener(valueChangeListener)

            if (getBaseCurrency() == rate.currency) {
                valueEditText.requestFocus()
            }
        }

        view.setOnClickListener {
            onItemClickListener(rate)
        }
        valueEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                onItemClickListener(rate)
            }
        }
    }

    fun unbind() {
        valueEditText.removeTextChangedListener(valueChangeListener)
        valueEditText.clearFocus()
        valueEditText.setOnFocusChangeListener(null)
    }

    fun onValueChanged(rate: RateViewData) {
        valueEditText.setText(rate.value)
    }
}