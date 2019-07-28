package com.brotandos.eposter.movies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brotandos.eposter.R
import com.brotandos.eposter.common.Utils
import com.brotandos.eposter.movies.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.onBind(movies[position])

    override fun getItemCount(): Int = movies.size

    fun setItems(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
    ) {
        private val posterImageView = itemView.posterImageView
        private val movieTitleTextView = itemView.movieTitleTextView
        private val releaseDateTextView = itemView.releaseDateTextView
        private val ratingTextView = itemView.ratingTextView

        fun onBind(movie: Movie) {
            movieTitleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate
            ratingTextView.text = movie.rating.toString()

            Glide.with(itemView)
                .load("${Utils.BASE_POSTER_PATH}${movie.posterPath}")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(posterImageView)
        }
    }
}