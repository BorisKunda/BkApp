package com.boriskunda.bkapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.boriskunda.bkapp.R
import com.boriskunda.bkapp.adapter.BkAdapter
import com.boriskunda.bkapp.data.Country
import com.boriskunda.bkapp.viewmodel.BkSharedViewModel


class BorderCountriesFragment : Fragment() {

    private val bkSharedViewModel: BkSharedViewModel by activityViewModels()
    private val borderCountryAdapter: BkAdapter = BkAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_border_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bkSharedViewModel.requestBorderCountriesList()

        bkSharedViewModel.borderCountriesListLd.observe(viewLifecycleOwner, {
            borderCountryAdapter.apply {
                countriesMutableList = it.toMutableList()
                notifyDataSetChanged()
            }

        })

    }

}