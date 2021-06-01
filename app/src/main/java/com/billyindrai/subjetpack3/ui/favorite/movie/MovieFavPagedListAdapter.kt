package com.billyindrai.subjetpack3.ui.favorite.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.databinding.ItemFilmBinding
import com.billyindrai.subjetpack3.ui.detail.DetailActivity
import com.billyindrai.subjetpack3.ui.movie.MovieAdapter
import com.bumptech.glide.Glide

internal class MovieFavPagedListAdapter : PagedListAdapter<MovieEntity, MovieFavPagedListAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK : DiffUtil.ItemCallback<MovieEntity> = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.favorite == newItem.favorite
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    internal  class  MovieViewHolder(private val binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieEntity){
                with(binding){
                    Glide.with(binding.root)
                        .load(MovieAdapter.URL +movie.poster)
                        .centerCrop()
                        .into(binding.ivPoster)
                    tvTitle.text = movie.title
                    tvRating.text = movie.rating.toString()
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position) as MovieEntity)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, getItem(holder.adapterPosition))
            intent.putExtra(DetailActivity.EXTRA_ID, DetailActivity.MOVIE_ID)
            it.context.startActivity(intent)
        }
    }

    fun getSwipedData(swipePosition: Int): MovieEntity? = getItem(swipePosition)

}