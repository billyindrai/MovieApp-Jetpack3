package com.billyindrai.subjetpack3.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.databinding.ItemFilmBinding
import com.billyindrai.subjetpack3.ui.detail.DetailActivity
import com.billyindrai.subjetpack3.ui.detail.DetailActivity.Companion.EXTRA_ID
import com.billyindrai.subjetpack3.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.billyindrai.subjetpack3.ui.detail.DetailActivity.Companion.MOVIE_ID
import com.bumptech.glide.Glide

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movieList = ArrayList<MovieEntity>()
    companion object{
        const val URL = "https://image.tmdb.org/t/p/w500/"
    }

    fun setMovie(movie: List<MovieEntity>?){
        if(movie == null) return
        movieList.clear()
        movieList.addAll(movie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        holder.bind(movieList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movieList[holder.adapterPosition])
            intent.putExtra(EXTRA_ID, MOVIE_ID)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(private val binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieEntity){
            with(binding){
                Glide.with(binding.root)
                    .load(URL+movie.poster)
                    .centerCrop()
                    .into(binding.ivPoster)
                tvTitle.text = movie.title
                tvRating.text = movie.rating.toString()
            }
        }
    }
}