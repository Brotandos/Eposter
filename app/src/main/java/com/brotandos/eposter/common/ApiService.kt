package com.brotandos.eposter.common

import com.brotandos.eposter.movies.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    fun getPopularMovies(
        @Query("page") page: Int
    ): Single<MovieResponse>
}