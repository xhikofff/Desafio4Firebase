package com.kaiodonadelli.desafio4firebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import com.kaiodonadelli.desafio4firebase.databinding.ActivityNewAccountBinding

class NewAccountActivity : AppCompatActivity() {

    private lateinit var bind: ActivityNewAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewAccountBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.buttonCreateAccount.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}