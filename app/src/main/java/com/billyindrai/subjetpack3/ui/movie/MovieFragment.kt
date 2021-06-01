package com.billyindrai.subjetpack3.ui.movie

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.billyindrai.subjetpack3.data.remote.api.Status
import com.billyindrai.subjetpack3.databinding.FragmentMovieBinding
import com.billyindrai.subjetpack3.ui.ViewModel

class MovieFragment : Fragment() {
    private lateinit var binding : FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var pagedAdapter: MoviePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            val factory = ViewModel.getInstance(view.context.applicationContext as Application)

            val viewModel = ViewModelProvider(
                this,
                factory
            )[MovieViewModel::class.java]

            movieAdapter = MovieAdapter()
            pagedAdapter = MoviePagerAdapter()

            viewModel.getMovieApi().observe(viewLifecycleOwner, { _movies ->
                if(_movies != null){
                    when(_movies.status){
                        Status.LOADING -> binding.pbMovie.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.pbMovie.visibility = View.GONE
                            pagedAdapter.submitList(_movies.data)

                            with(binding.rvMovie){
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