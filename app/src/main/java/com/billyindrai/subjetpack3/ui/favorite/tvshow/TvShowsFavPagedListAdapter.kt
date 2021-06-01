package com.billyindrai.subjetpack3.ui.favorite.tvshow

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.databinding.ItemFilmBinding
import com.billyindrai.subjetpack3.ui.detail.DetailActivity
import com.billyindrai.subjetpack3.ui.tvshow.TvShowsAdapter
import com.bumptech.glide.Glide

internal class TvShowsFavPagedListAdapter : PagedListAdapter<TvShowsEntity, TvShowsFavPagedListAdapter.TvShowsViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK : DiffUtil.ItemCallback<TvShowsEntity> = object : DiffUtil.ItemCallback<TvShowsEntity>(){
            override fun areItemsTheSame(oldItem: TvShowsEntity, newItem: TvShowsEntity): Boolean {
                return oldItem.favorite == newItem.favorite
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TvShowsEntity, newItem: TvShowsEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    internal  class  TvShowsViewHolder(private val binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tv: TvShowsEntity){
            with(binding){
                Glide.with(binding.root)
                    .load(TvShowsAdapter.URL +tv.poster)
                    .centerCrop()
                    .into(binding.ivPoster)
                tvTitle.text = tv.title
                tvRating.text = tv.rating.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        holder.bind(getItem(position) as TvShowsEntity)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_TV, getItem(holder.adapterPosition))
            intent.putExtra(DetailActivity.EXTRA_ID, DetailActivity.TV_ID)
            it.context.startActivity(intent)
        }
    }

    fun getSwipedData(swipePosition: Int): TvShowsEntity? = getItem(swipePosition)
}