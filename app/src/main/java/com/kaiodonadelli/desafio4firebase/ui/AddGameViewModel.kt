package com.kaiodonadelli.desafio4firebase.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.kaiodonadelli.desafio4firebase.domain.Game
import com.google.firebase.firestore.FirebaseFirestore

class AddGameViewModel(val context: Context) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun uploadGame(game: Game) {
        db.collection("game")
            .add(game.toMap())
            .addOnSuccessListener {
                Toast.makeText(context, "Uploaded succeeded!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Upload failed.", Toast.LENGTH_SHORT).show()
                Log.e(MainActivity.TAG, "Erro na criação.", it)
            }
    }

        fun updateGame(game: Game) {
            db.collection("game")
                .document(game.id)
                .update(game.toMap())
                .addOnSuccessListener {
                    Toast.makeText(context, "Save succeeded!", Toast.LENGTH_SHORT).show()
                    Log.d(MainActivity.TAG, "${game.id} atualizado")
                }
            .addOnFailureListener {
                Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT).show()
                Log.e(MainActivity.TAG, "Erro ao salvar", it)
            }
    }
}