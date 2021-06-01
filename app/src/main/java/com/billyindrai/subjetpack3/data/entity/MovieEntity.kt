package com.billyindrai.subjetpack3.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "posterPath")
    var poster: String,

    @ColumnInfo(name = "originalTitle")
    var title: String,

    @ColumnInfo(name = "releaseDate")
    var date: String,

    @ColumnInfo(name = "voteAverage")
    var rating: Double,

    @ColumnInfo(name = "runtime")
    var duration: Int,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
): Parcelable
