package com.kaiodonadelli.desafio4firebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaiodonadelli.desafio4firebase.R
import com.kaiodonadelli.desafio4firebase.databinding.ActivityGameDetailsBinding
import com.kaiodonadelli.desafio4firebase.domain.Game
import com.squareup.picasso.Picasso

class GameDetailsActivity : AppCompatActivity() {

    private lateinit var bind: ActivityGameDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.buttonBack.setOnClickListener {
            onBackPressed()
        }

        val game = intent.getSerializableExtra("game") as? Game ?: return

        bind.textViewTitle.text = game.name
        bind.textViewGameName.text = game.name
        bind.textViewReleaseDate.text = getString(R.string.game_release).format(game.releaseDate)
        bind.textViewDescription.text = game.description

        Picasso.get()
            .load(game.imageUrl)
            .resize(500, 500)
            .centerCrop()
            .into(bind.imageViewHero)

        bind.buttonEdit.setOnClickListener {
            startActivity(Intent(this, AddGameActivity::class.java).apply {
                putExtra("game", game)
            })
            finish()
        }
    }
}