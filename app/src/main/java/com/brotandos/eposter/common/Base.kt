package com.brotandos.eposter.common

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

interface BaseUI

abstract class BasePresenter<UI : BaseUI> : ViewModel() {

    protected abstract var ui: UI?

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.disposeOnCleared() = compositeDisposable.add(this)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @CallSuper
    open fun onCreate() = Unit

    /**
     * Need to prevent leak
     * */
    @CallSuper
    open fun onDestroy() {
        ui = null
        compositeDisposable.clear()
    }
}

abstract class BaseActivity<UI : BaseUI> : AppCompatActivity(), BaseUI {

    protected abstract val presenter: BasePresenter<UI>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    inline fun <reified T : ViewModel> presenter() = viewModelByClass(T::class) {
        @Suppress("UNCHECKED_CAST")
        (parametersOf(this as UI))
    }
}

abstract class BaseFragment<UI : BaseUI> : Fragment(), BaseUI {

    protected abstract val presenter: BasePresenter<UI>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    inline fun <reified T : ViewModel> presenter() = viewModelByClass(T::class) {
        @Suppress("UNCHECKED_CAST")
        (parametersOf(this as UI))
    }
}