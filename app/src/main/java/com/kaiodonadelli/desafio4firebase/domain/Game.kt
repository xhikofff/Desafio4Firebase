package com.kaiodonadelli.desafio4firebase.domain

import java.io.Serializable

class Game(
    val name: String,
    val imageId: String,
    val imageUrl: String,
    val releaseDate: String,
) : Serializable