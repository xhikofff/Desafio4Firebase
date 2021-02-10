package com.kaiodonadelli.desafio4firebase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaiodonadelli.desafio4firebase.R
import com.kaiodonadelli.desafio4firebase.domain.Game
import com.squareup.picasso.Picasso

class MainAdapter(
    private val onGameClicked: (g: Game) -> Unit,
) : RecyclerView.Adapter<MainAdapter.GameViewHolder>() {

    private var listGames: List<Game> = listOf()

    inner class GameViewHolder(gameView: View) : RecyclerView.ViewHolder(gameView) {
        val imageViewGame: ImageView = gameView.findViewById(R.id.imageViewGame)
        val textViewName: TextView = gameView.findViewById(R.id.textViewName)
        val textViewReleaseDate: TextView = gameView.findViewById(R.id.textViewReleaseDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val gameView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_game,
            parent,
            false
        )

        return GameViewHolder(gameView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = listGames[position]

        Picasso.get()
            .load(game.imageUrl)
            .centerCrop()
            .resize(400, 400)
            .into(holder.imageViewGame)

        holder.textViewName.text = game.name
        holder.textViewReleaseDate.text = game.releaseDate

        holder.itemView.setOnClickListener {
            onGameClicked(game)
        }
    }

    override fun getItemCount(): Int = listGames.size

    fun updateGameList(games: List<Game>) {
        listGames = games
        notifyDataSetChanged()
    }
}