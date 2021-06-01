package com.billyindrai.subjetpack3.ui.favorite.tvshow

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
import com.billyindrai.subjetpack3.databinding.FragmentTvShowsFavBinding
import com.billyindrai.subjetpack3.ui.ViewModel
import com.billyindrai.subjetpack3.ui.tvshow.TvShowsViewModel
import com.google.android.material.snackbar.Snackbar

class TvShowsFavFragment : Fragment() {
    private lateinit var binding: FragmentTvShowsFavBinding
    private lateinit var tvViewModel: TvShowsViewModel
    private lateinit var tvFavAdapter: TvShowsFavAdapter
    private lateinit var tvPagedAdapter: TvShowsFavPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowsFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvTvFav)


        val factory = ViewModel.getInstance(view.context.applicationContext as Application)
        tvViewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]
        tvFavAdapter = TvShowsFavAdapter()

        tvPagedAdapter = TvShowsFavPagedListAdapter()

        tvViewModel.getListTvShowFavorite().observe(viewLifecycleOwner, { listTv ->
            tvPagedAdapter.submitList(listTv)

            with(binding.rvTvFav){
                layoutManager = LinearLayoutManager(view.context)
                setHasFixedSize(true)
                adapter = tvPagedAdapter
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
                val dataEntity = tvPagedAdapter.getSwipedData(swipedPosition)
                dataEntity?.let { tvViewModel.deleteTvShows(it)}

                val snackBar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_done) { v ->
                    dataEntity?.let { tvViewModel.deleteTvShows(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_title_asc -> sort = Sorting.TITLE_TV
            R.id.action_top_rating -> sort = Sorting.TOP_RATING
        }
        observeData(Sorting.TYPE_TVSHOW, sort)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun observeData(type: String, sort: String){
        when (type) {
            Sorting.TYPE_TVSHOW -> {
                tvViewModel.getAllTVShowsFromDb(sort).observe(viewLifecycleOwner, { listTv ->
                    if (listTv != null) {
                        tvPagedAdapter.submitList(listTv)
                        binding.pbTvFav.visibility = View.GONE
                    }
                })
            }
        }
    }
}