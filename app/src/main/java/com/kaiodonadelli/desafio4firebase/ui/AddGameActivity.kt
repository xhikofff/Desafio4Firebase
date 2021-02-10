package com.kaiodonadelli.desafio4firebase.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kaiodonadelli.desafio4firebase.databinding.ActivityAddGameBinding
import com.kaiodonadelli.desafio4firebase.domain.Game
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*


class AddGameActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_CODE = 1000
    }

    private val addGameViewModel by viewModels<AddGameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AddGameViewModel(this@AddGameActivity) as T
            }
        }
    }

    private lateinit var bind: ActivityAddGameBinding
    private lateinit var alertDialog: AlertDialog

    private var gameId: String = ""
    private var gameImageId: String = ""
    private var gameImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        alertDialog = SpotsDialog.Builder().setContext(this).build()

        (intent.getSerializableExtra("game") as Game?)?.let { game ->
            bind.inputName.setText(game.name)
            bind.inputReleaseDate.setText(game.releaseDate)
            bind.inputDescription.setText(game.description)

            Picasso.get()
                .load(game.imageUrl)
                .resize(400, 400)
                .centerCrop()
                .into(bind.buttonAddImage)

            gameId = game.id
            gameImageId = game.imageId
            gameImageUrl = game.imageUrl

            bind.buttonDeleteGame.visibility = View.VISIBLE
            bind.buttonDeleteGame.setOnClickListener {
                addGameViewModel.deleteGame(game)
            }
        }

        bind.buttonAddImage.setOnClickListener {
            uploadImage()
        }

        bind.buttonSaveGame.setOnClickListener {
            val gameName = bind.inputName.text.toString()
            val gameRelease = bind.inputReleaseDate.text.toString()
            val gameDescription = bind.inputDescription.text.toString()

            if (gameName == "" || gameRelease == "" || gameDescription == "" || gameImageUrl == "") {
                Toast.makeText(
                    this,
                    "Please fill the required fields.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val game =
                Game(gameName, "test", gameImageId, gameImageUrl, gameRelease, gameDescription)

            if (gameId != "") {
                game.id = gameId
                addGameViewModel.updateGame(game)
                return@setOnClickListener
            }

            addGameViewModel.uploadGame(game)
        }
    }

    private fun uploadImage() {
        val intent = Intent().apply {
            type = "image/"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Captura imagem"), IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (gameImageId == "") {
            gameImageId = UUID.randomUUID().toString()
        } else {
            Picasso.get().invalidate(gameImageUrl)
        }

        val storageReference = FirebaseStorage.getInstance().getReference(gameImageId)

        if (requestCode == IMAGE_CODE) {
            alertDialog.show()
            val uploadTask = storageReference.putFile(data!!.data!!)
            uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Uploading", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl

            }.addOnSuccessListener {
                val url = it.toString().substring(0, it.toString().indexOf("&token"))

                alertDialog.dismiss()
                Picasso.get()
                    .load(url)
                    .resize(400, 400)
                    .centerCrop()
                    .into(bind.buttonAddImage)

                gameImageUrl = url

            }.addOnFailureListener {
                Log.e("Erro no upload", it.toString())
                alertDialog.dismiss()
                Toast.makeText(this, "Image upload failed. Please try again.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}