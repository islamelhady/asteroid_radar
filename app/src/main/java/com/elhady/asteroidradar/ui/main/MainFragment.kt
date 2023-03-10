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
import com.elhady.asteroidradar.ui.main.viewmodels.MainViewModel
import com.elhady.asteroidradar.ui.main.viewmodels.MainViewModelFactory

class MainFragment : Fragment() {

    private var adapter: AsteroidAdapter? = null
    private lateinit var binding: FragmentMainBinding

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainViewModelFactory(activity.application)).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        setAdapter()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            mainViewModel = viewModel
        }
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_today_menu -> {
                viewModel.getTodayAsteroids().observe(viewLifecycleOwner, Observer {
                    adapter!!.submitList(it)
                })
            }
            R.id.show_week_menu -> {
                viewModel.getWeekAsteroids().observe(viewLifecycleOwner, Observer {
                    adapter!!.submitList(it)
                })
            }
            R.id.show_saved_menu -> {
                viewModel.asteroid.observe(viewLifecycleOwner, Observer {
                    adapter!!.submitList(it)
                })
            }
        }
        return true
    }

}
