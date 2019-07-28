package com.brotandos.eposter.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.brotandos.eposter.R
import com.brotandos.eposter.common.BaseFragment
import com.brotandos.eposter.common.BaseUI
import com.brotandos.eposter.movies.presenter.MoviesPresenter
import com.brotandos.eposter.movies.model.Movie
import kotlinx.android.synthetic.main.fragment_movies.moviesRecyclerView
import kotlinx.android.synthetic.main.fragment_movies.progressBar
import org.jetbrains.anko.toast

class MoviesFragment : BaseFragment<MoviesUI>(), MoviesUI {

    private val adapter = MoviesAdapter()

    override val presenter: MoviesPresenter by presenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesRecyclerView.adapter = adapter
    }

    override fun showLoading() {
        progressBar.isVisible = true
    }

    override fun showMovies(movies: List<Movie>) {
        progressBar.isVisible = false
        adapter.setItems(movies)
    }

    override fun onError(message: String) {
        progressBar.isVisible = false
        context?.toast(message)
    }
}

interface MoviesUI : BaseUI {

    fun showLoading()

    fun showMovies(movies: List<Movie>)

    fun onError(message: String)
}