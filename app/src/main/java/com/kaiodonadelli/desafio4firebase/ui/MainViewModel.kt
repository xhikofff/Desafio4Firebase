package com.kaiodonadelli.desafio4firebase.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaiodonadelli.desafio4firebase.domain.Game
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainViewModel : ViewModel() {

    val listGames = MutableLiveData<List<Game>>()
    private val allGames = mutableListOf<Game>()
    private val db = FirebaseFirestore.getInstance()


    fun getAllGames() {
        db.collection("game")
            .get()
            .addOnSuccessListener {
                allGames.clear()
                if (it.isEmpty) {
                    return@addOnSuccessListener
                }
                it.forEach { document ->
                    allGames.add(Game.fromDocument(document))
                }

                listGames.value = allGames
            }
            .addOnFailureListener {
                Log.w(MainActivity.TAG, "Erro ao ler documentos", it)
            }
    }

    fun filterGames(filterText: String) {
        listGames.value = allGames.filter {
            it.name.toLowerCase(Locale.ROOT).contains(filterText.toLowerCase(Locale.ROOT))
        }
    }

    fun clearFilter() {
        listGames.value = allGames
    }
}