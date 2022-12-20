package com.elhady.asteroidradar.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.elhady.asteroidradar.R
import com.elhady.asteroidradar.databinding.FragmentDetailBinding
import com.elhady.asteroidradar.databinding.FragmentMainBinding


class DetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater)


        return binding.root
    }


}