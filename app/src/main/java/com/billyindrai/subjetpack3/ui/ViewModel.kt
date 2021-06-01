package com.billyindrai.subjetpack3.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billyindrai.subjetpack3.Injection
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.ui.movie.MovieViewModel
import com.billyindrai.subjetpack3.ui.tvshow.TvShowsViewModel

class ViewModel (private val repository: Repository): ViewModelProvider.NewInstanceFactory()  {
    companion object {
        @Volatile
        private var instance: com.billyindrai.subjetpack3.ui.ViewModel? = null

        fun getInstance(application: Application): com.billyindrai.subjetpack3.ui.ViewModel =
            instance ?: synchronized(this) {
                instance ?: ViewModel(Injection.repository(application)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) -> {
                return TvShowsViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}