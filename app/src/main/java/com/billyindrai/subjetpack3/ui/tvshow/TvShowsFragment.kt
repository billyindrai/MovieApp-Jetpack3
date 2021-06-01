package com.billyindrai.subjetpack3.ui.tvshow

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.billyindrai.subjetpack3.data.remote.api.Status
import com.billyindrai.subjetpack3.databinding.FragmentTvShowsBinding
import com.billyindrai.subjetpack3.ui.ViewModel

class TvShowsFragment : Fragment() {
    private lateinit var binding: FragmentTvShowsBinding
    private lateinit var tvAdapter: TvShowsAdapter
    private lateinit var pagedAdapter: TvShowsPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            val factory = ViewModel.getInstance(view.context.applicationContext as Application)
            val viewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]

            tvAdapter = TvShowsAdapter()
            pagedAdapter = TvShowsPagerAdapter()

            viewModel.getTvShowApi().observe(viewLifecycleOwner, { tvShow ->
                if(tvShow != null){
                    when(tvShow.status){
                        Status.LOADING -> binding.pbTv.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.pbTv.visibility = View.GONE

                            pagedAdapter.submitList(tvShow.data)
                            with(binding.rvTv){
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = pagedAdapter
                            }
                        }
                    }
                }
            })

        }
    }

}