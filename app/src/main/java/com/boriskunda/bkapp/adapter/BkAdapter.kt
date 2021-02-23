package com.boriskunda.bkapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boriskunda.bkapp.R
import com.boriskunda.bkapp.data.Country

class BkAdapter(
    private val onRVItemClickListener: OnRVItemClickListener
) :
    RecyclerView.Adapter<BkAdapter.BkViewHolder>() {

    var countriesMutableList: MutableList<Country> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.country_list_item, parent, false)
        return BkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BkViewHolder, position: Int) {
        val country = countriesMutableList[position]

        holder.apply {
            countryNativeNameTv.text = country.nativeName
            countryEnglishNameTv.text = country.englishName
            //---
            itemView.setOnClickListener {
                onRVItemClickListener.onRVItemClick(country)
            }
        }

    }

    override fun getItemCount(): Int = countriesMutableList.size


    class BkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryNativeNameTv: TextView = itemView.findViewById(R.id.native_name_tv)
        val countryEnglishNameTv: TextView = itemView.findViewById(R.id.english_name_tv)
    }

    interface OnRVItemClickListener {
        fun onRVItemClick(country: Country)
    }


}
