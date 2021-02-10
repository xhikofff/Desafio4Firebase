package com.kaiodonadelli.desafio4firebase.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kaiodonadelli.desafio4firebase.databinding.ActivityMainBinding
import com.kaiodonadelli.desafio4firebase.domain.Game


class MainActivity : AppCompatActivity() {

    private val mainAdapter = MainAdapter(this, ::onGameClicked)
    private lateinit var bind: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.recyclerViewGames.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(false)
        }
    }


    private fun onGameClicked(game: Game) {
        Log.d(TAG, game.toString())
    }
}