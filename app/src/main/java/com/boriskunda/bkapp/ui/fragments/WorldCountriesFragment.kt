package com.boriskunda.bkapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.boriskunda.bkapp.R
import com.boriskunda.bkapp.viewmodel.BkSharedViewModel


class WorldCountriesFragment : Fragment() {

    private val bkSharedViewModel: BkSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_world_countries, container, false)



        // Inflate the layout for this fragment
        return view
    }
    
}