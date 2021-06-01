package com.billyindrai.subjetpack3.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.billyindrai.subjetpack3.R
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.databinding.ActivityDetailBinding
import com.billyindrai.subjetpack3.ui.ViewModel
import com.billyindrai.subjetpack3.ui.movie.MovieAdapter.Companion.URL
import com.billyindrai.subjetpack3.ui.movie.MovieViewModel
import com.billyindrai.subjetpack3.ui.tvshow.TvShowsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_TV = "EXTRA_TV"
        const val EXTRA_ID = "EXTRA_ID"

        const val MOVIE_ID = 0
        const val TV_ID = 1
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movie: MovieEntity
    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var tvShow: TvShowsEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.extras

        val factory = ViewModel.getInstance(application)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        tvShowsViewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]

        if(intent != null){
            val id = intent.getInt(EXTRA_ID, 0)
            if(id == 0){
                movie = intent.getParcelable<MovieEntity>(EXTRA_MOVIE) as MovieEntity
                setActionBarTittle("Movie")
                movieViewModel.getMovieById(movie.id).observe(this, { movie ->
                    bindViewMovie(movie)
                    checkState(movie.favorite)

                    binding.btnFavorite.setOnClickListener {view ->
                        if(movie.favorite){
                            removeMovieFromFavorite(movie)
                            Snackbar.make(view, getString(R.string.remove_favorite), Snackbar.LENGTH_SHORT).show()
                        } else {
                            addMovieToFavorite(movie)
                            Snackbar.make(view, getString(R.string.add_favorite), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                tvShow = intent.getParcelable<TvShowsEntity>(EXTRA_TV) as TvShowsEntity
                setActionBarTittle("Tv Show")

                tvShowsViewModel.getTvShowById(tvShow.id).observe(this, { tvShow ->
                    bindViewTv(tvShow)
                    checkState(tvShow.favorite)

                    binding.btnFavorite.setOnClickListener {view ->
                        if(tvShow.favorite){
                            removeTvShowFromFavorite(tvShow)
                            Snackbar.make(view, getString(R.string.remove_favorite), Snackbar.LENGTH_SHORT).show()
                        } else {
                            addTvShowToFavorite(tvShow)
                            Snackbar.make(view, getString(R.string.add_favorite), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun bindViewMovie(movie: MovieEntity){
        with(binding){
            Glide.with(binding.root)
                .load(URL + movie.poster)
                .override(300, 400)
                .into(ivDetail)
            tvTitleDetail.text = movie.title
            tvRatingDetail.text = movie.rating.toString()
            tvDateDetail.text = movie.date
            tvDurationDetail.text = getString(R.string.duration, movie.duration.toString())
            tvDescriptionsDetail.text = movie.overview
            btnFavorite.visibility = View.VISIBLE
        }
    }

    private fun bindViewTv(tv: TvShowsEntity){
        with(binding){
            Glide.with(binding.root)
                .load(URL + tv.poster)
                .override(300, 400)
                .into(ivDetail)
            tvTitleDetail.text = tv.title
            tvRatingDetail.text = tv.rating.toString()
            tvDateDetail.text = tv.date
            tvDurationDetail.text = getString(R.string.episodes, tv.episodes.toString())
            tvDescriptionsDetail.text = tv.overview
            btnFavorite.visibility = View.VISIBLE
        }
    }

    fun addMovieToFavorite(movie: MovieEntity) {
        movie.favorite = true
        movieViewModel.addToFavorite(movie)
    }

    fun addTvShowToFavorite(tvShow: TvShowsEntity){
        tvShow.favorite = true
        tvShowsViewModel.addToFavorite(tvShow)
    }

    fun removeMovieFromFavorite(movie: MovieEntity){
        movie.favorite = false
        movieViewModel.addToFavorite(movie)
    }

    fun removeTvShowFromFavorite(tvShow: TvShowsEntity){
        tvShow.favorite = false
        tvShowsViewModel.addToFavorite(tvShow)
    }

    private fun setActionBarTittle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    fun checkState(state: Boolean) {
        return if(state){
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }else {
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

}
