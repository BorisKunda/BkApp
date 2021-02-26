package com.boriskunda.bkapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.boriskunda.bkapp.R
import com.boriskunda.bkapp.viewmodel.BkSharedViewModel


class BorderCountriesFragment : Fragment() {

    private val bkSharedViewModel: BkSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_border_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bkSharedViewModel.get

        bkSharedViewModel.borderCountriesListLd.observe(viewLifecycleOwner, {

        })

    }

}