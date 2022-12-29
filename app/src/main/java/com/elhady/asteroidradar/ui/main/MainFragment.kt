package com.elhady.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.elhady.asteroidradar.R
import com.elhady.asteroidradar.databinding.FragmentMainBinding
import com.elhady.asteroidradar.ui.main.adapters.AsteroidAdapter
import com.elhady.asteroidradar.ui.main.adapters.AsteroidClick
import timber.log.Timber

class MainFragment : Fragment() {

    private var adapter: AsteroidAdapter? = null
    private lateinit var binding: FragmentMainBinding


    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this,MainViewModelFactory(activity.application)).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        observeAsteroid()
        setAdapter()

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setAdapter() {
        adapter = AsteroidAdapter(AsteroidClick{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
        })

        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroid.observe(viewLifecycleOwner, Observer {
            adapter!!.submitList(it)
        })
    }

    private fun observeAsteroid() {
        viewModel.asteroid.observe(viewLifecycleOwner, Observer {
            binding.textView.text = it.size.toString()
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

}
