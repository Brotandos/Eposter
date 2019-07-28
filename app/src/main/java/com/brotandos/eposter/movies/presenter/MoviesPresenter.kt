package com.brotandos.eposter.movies.presenter

import com.brotandos.eposter.common.BasePresenter
import com.brotandos.eposter.movies.model.MoviesRepository
import com.brotandos.eposter.movies.view.MoviesUI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MoviesPresenter(
    override var ui: MoviesUI?,
    private val moviesRepository: MoviesRepository
) : BasePresenter<MoviesUI>() {

    override fun onCreate() {
        super.onCreate()
        loadMovies()
    }

    private fun loadMovies() {
        moviesRepository.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { ui?.showLoading() }
            .subscribe(
                { ui?.showMovies(it.movies) },
                {
                    Timber.e(it, "Error while loading movies")
                    it.message?.let { message -> ui?.onError(message) }
                }
            )
            .disposeOnCleared()
    }
}