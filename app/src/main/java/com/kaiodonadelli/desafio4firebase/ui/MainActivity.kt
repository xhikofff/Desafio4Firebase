package com.kaiodonadelli.desafio4firebase.ui

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kaiodonadelli.desafio4firebase.databinding.ActivityMainBinding
import com.kaiodonadelli.desafio4firebase.domain.Game
import androidx.activity.viewModels
import android.widget.SearchView

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val mainViewModel by viewModels<MainViewModel>()

    private val mainAdapter = MainAdapter(::onGameClicked)
    private lateinit var bind: ActivityMainBinding

    private var lastFilterText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.recyclerViewGames.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(false)
        }

        bind.searchViewGames.apply {
            isIconifiedByDefault = false

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query == null) return false

                    filterGames(query)

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null) return false

                    if (newText.length >= 3) {
                        filterGames(newText)
                    } else if (newText.isEmpty()) {
                        mainViewModel.clearFilter()
                    }

                    return false
                }
            })

        }

        mainViewModel.listGames.observe(this) {
            mainAdapter.updateGameList(it)
        }

        bind.buttonAddGame.setOnClickListener {
            startActivity(Intent(this, AddEditGameActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        mainViewModel.getAllGames()
    }

    private fun filterGames(filterText: String) {
        if (lastFilterText == filterText) return

        lastFilterText = filterText
        mainViewModel.filterGames(filterText)
    }

    private fun onGameClicked(game: Game) {
        startActivity(Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game", game)
        })
    }
}