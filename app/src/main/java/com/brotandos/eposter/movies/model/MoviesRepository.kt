package com.brotandos.eposter.movies.model

import com.brotandos.eposter.common.ApiService
import com.brotandos.eposter.BuildConfig

class MoviesRepository(private val apiService: ApiService) {

    fun getPopularMovies() = apiService.getPopularMovies(1)
}