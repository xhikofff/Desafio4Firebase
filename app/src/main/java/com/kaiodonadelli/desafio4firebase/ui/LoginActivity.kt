package com.kaiodonadelli.desafio4firebase.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import com.kaiodonadelli.desafio4firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val pref = getSharedPreferences("login", Context.MODE_PRIVATE)
        val rememberMe = pref.getBoolean("remember_me", true)

        bind.checkRememberMe.isChecked = rememberMe

        bind.checkRememberMe.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean("remember_me", isChecked).apply()
        }

        bind.buttonLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        bind.textViewCreateAccount.setOnClickListener {
            startActivity(Intent(this, NewAccountActivity::class.java))
        }

    }


}