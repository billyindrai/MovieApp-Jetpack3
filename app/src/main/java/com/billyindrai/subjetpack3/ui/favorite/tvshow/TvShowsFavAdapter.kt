package com.billyindrai.subjetpack3.ui.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.databinding.ItemFilmBinding
import com.billyindrai.subjetpack3.ui.detail.DetailActivity
import com.billyindrai.subjetpack3.ui.detail.DetailActivity.Companion.EXTRA_TV
import com.billyindrai.subjetpack3.ui.tvshow.TvShowsAdapter
import com.bumptech.glide.Glide

class TvShowsFavAdapter :  PagedListAdapter<TvShowsEntity,TvShowsFavAdapter.TvShowViewHolder>(DIFF_CALLBACK) {
    private var tvList  = ArrayList<TvShowsEntity>()

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowsEntity>(){
            override fun areItemsTheSame(oldItem: TvShowsEntity, newItem: TvShowsEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowsEntity, newItem: TvShowsEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    fun setTvShow (tv: List<TvShowsEntity>?){
        if(tv == null) return
        tvList.clear()
        tvList.addAll(tv)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)

    }
    override fun getItemCount(): Int {
        return tvList.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(EXTRA_TV, tvList[holder.adapterPosition])
            intent.putExtra(DetailActivity.EXTRA_ID, DetailActivity.TV_ID)
            it.context.startActivity(intent)
        }
    }

    class TvShowViewHolder(private val binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShows: TvShowsEntity){
            with(binding){
                Glide.with(binding.root)
                    .load(TvShowsAdapter.URL +tvShows.poster)
                    .centerCrop()
                    .into(binding.ivPoster)
                tvTitle.text = tvShows.title
                tvRating.text = tvShows.rating.toString()
            }
        }
    }


}