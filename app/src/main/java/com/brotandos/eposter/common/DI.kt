package com.brotandos.eposter.common

import com.brotandos.eposter.BuildConfig
import com.brotandos.eposter.movies.presenter.MoviesPresenter
import com.brotandos.eposter.movies.model.MoviesRepository
import com.brotandos.eposter.movies.view.MoviesUI
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DI {

    private val presenterModule = module {
        viewModel { (ui: MoviesUI) -> MoviesPresenter(ui, get()) }
    }

    private val repositoryModule = module {
        single { MoviesRepository(get()) }
    }

    private val retrofitModule = module {

        single {
            OkHttpClient.Builder()
                .addInterceptor(getHeaderInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        single {
            Retrofit.Builder()
                .client(get()) // OkHttpClient
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        single { get<Retrofit>().create(ApiService::class.java) }
    }

    val modules = listOf(
        presenterModule,
        repositoryModule,
        retrofitModule
    )

    private fun getHeaderInterceptor() = Interceptor {
        val originalRequest = it.request()
        val url = originalRequest.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val request = originalRequest.newBuilder().url(url).build()
        it.proceed(request)
    }
}