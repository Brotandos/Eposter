package com.brotandos.eposter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brotandos.eposter.movies.view.MoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MoviesFragment())
            .commit()
    }
}