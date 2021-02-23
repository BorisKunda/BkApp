package com.boriskunda.bkapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.boriskunda.bkapp.R
import com.boriskunda.bkapp.adapter.BkAdapter
import com.boriskunda.bkapp.data.Country
import com.boriskunda.bkapp.viewmodel.BkSharedViewModel
import kotlinx.android.synthetic.main.fragment_world_countries.*


class WorldCountriesFragment : Fragment(), BkAdapter.OnRVItemClickListener {

    private val bkSharedViewModel: BkSharedViewModel by activityViewModels()
    private val worldCountryAdapter: BkAdapter = BkAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        world_countries_rv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = worldCountryAdapter
        }

        bkSharedViewModel.apply {
            requestWorldCountriesList()
            //---
            worldCountriesListLd.observe(viewLifecycleOwner, {
                worldCountryAdapter.apply {
                    countriesMutableList = it.toMutableList()
                    notifyDataSetChanged()
                }
            })

        }

    }

    override fun onRVItemClick(country: Country) {

    }

}