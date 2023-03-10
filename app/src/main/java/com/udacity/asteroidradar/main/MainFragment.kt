package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this,MainViewModelFactory(activity.application)).get(MainViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainRecyclerViewAdapter(OnclickListener {
            viewModel.displayAsteroidDetails(it)
        })
        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()

            }
        })
        binding.asteroidRecycler.adapter = adapter

        setHasOptionsMenu(true)
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
           it?.let {
                if (!it.isEmpty()){
                    viewModel.setStatusWithDone()
                }
           }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week_menu->{viewModel.getWeekAsteroid()}
            R.id.show_today_menu->{viewModel.getTodayAsteroid()}
            R.id.show_saved_menu->{viewModel.getSavedAsteroid()}
        }
        return true
    }
}
