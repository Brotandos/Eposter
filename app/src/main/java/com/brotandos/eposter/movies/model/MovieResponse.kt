package com.brotandos.eposter.movies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @Expose
    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
)