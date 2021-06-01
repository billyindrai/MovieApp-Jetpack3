package com.billyindrai.subjetpack3

import androidx.sqlite.db.SimpleSQLiteQuery

object Sorting {
    const val TITLE_MOVIE = "TITLE_MOVIE"
    const val TITLE_TV = "TITLE_TV"
    const val TOP_RATING = "TOP_RATING"
    const val TYPE_MOVIE = "TYPE_MOVIE"
    const val TYPE_TVSHOW = "TYPE_TVSHOW"

    fun getSortedQuery(type: String, filter: String): SimpleSQLiteQuery {
        val simpleQuery: StringBuilder = StringBuilder()
        when (type) {
            TYPE_MOVIE -> simpleQuery.append("SELECT * FROM movieentity ")
            TYPE_TVSHOW -> simpleQuery.append("SELECT * FROM tvshowsentity ")
        }

        when (filter) {
            TITLE_MOVIE -> simpleQuery.append("WHERE favorite = 1 ORDER BY originalTitle ASC ")
            TITLE_TV -> simpleQuery.append("WHERE favorite = 1 ORDER BY originalName ASC ")
            TOP_RATING -> simpleQuery.append("WHERE favorite = 1 ORDER BY voteAverage DESC ")
        }

        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}