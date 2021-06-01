package com.billyindrai.subjetpack3.ui.favorite.movie

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.billyindrai.subjetpack3.R
import com.billyindrai.subjetpack3.Sorting
import com.billyindrai.subjetpack3.databinding.FragmentMovieFavBinding
import com.billyindrai.subjetpack3.ui.ViewModel
import com.billyindrai.subjetpack3.ui.movie.MovieViewModel
import com.google.android.material.snackbar.Snackbar

class MovieFavFragment : Fragment() {
    private lateinit var binding: FragmentMovieFavBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieFavAdapter: MovieFavAdapter
    private lateinit var pagedAdapter: MovieFavPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvMovieFav)

        val factory = ViewModel.getInstance(view.context.applicationContext as Application)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        movieFavAdapter = MovieFavAdapter()

        pagedAdapter = MovieFavPagedListAdapter()

        movieViewModel.getListMovieFavorite().observe(viewLifecycleOwner, { listMovie ->
            pagedAdapter.submitList(listMovie)

            with(binding.rvMovieFav){
                layoutManager = LinearLayoutManager(view.context)
                setHasFixedSize(true)
                adapter = pagedAdapter

            }
        })
    }
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val dataEntity = pagedAdapter.getSwipedData(swipedPosition)
                dataEntity?.let { movieViewModel.deleteMovie(it)}

                val snackBar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_done) { v ->
                    dataEntity?.let { movieViewModel.deleteMovie(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_title_asc -> sort = Sorting.TITLE_MOVIE
            R.id.action_top_rating -> sort = Sorting.TOP_RATING
        }
        observeData(Sorting.TYPE_MOVIE, sort)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun observeData(type: String, sort: String){
        when (type) {
            Sorting.TYPE_MOVIE -> {
                movieViewModel.getAllMoviesFromDb(sort).observe(viewLifecycleOwner, { listMovies ->
                    if (listMovies != null) {
                        pagedAdapter.submitList(listMovies)
                        binding.pbMovieFav.visibility = View.GONE
                    }
                })
            }
        }
    }
}