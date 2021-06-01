package com.billyindrai.subjetpack3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TvShowsEntity (
    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "originalName")
    var title: String,

    @ColumnInfo(name = "posterPath")
    var poster: String,

    @ColumnInfo(name = "firstAirDate")
    var date: String,

    @ColumnInfo(name = "voteAverage")
    var rating: Double,

    @ColumnInfo(name = "number_of_episodes")
    var episodes: Int,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
): Parcelable