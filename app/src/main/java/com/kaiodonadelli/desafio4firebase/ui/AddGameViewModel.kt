package com.kaiodonadelli.desafio4firebase.ui

import android.content.Context
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.kaiodonadelli.desafio4firebase.domain.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


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

    fun deleteGame(game: Game) {
        db.collection("game")
            .document(game.id)
            .delete()
            .addOnSuccessListener {
                val storageReference = FirebaseStorage.getInstance().getReference(game.imageId)
                storageReference.delete()
                Toast.makeText(context, "Game deleted.", Toast.LENGTH_SHORT).show()
                (context as Activity).finish()
                Log.d(MainActivity.TAG, "${game.id} excluído")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Delete failed.", Toast.LENGTH_SHORT).show()
                Log.d(MainActivity.TAG, "Erro ao excluir ${game.id}")
            }
    }
}