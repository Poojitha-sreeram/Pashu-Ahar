package com.example.pashuahar

import java.io.Serializable

data class FeedRecipe(

    val breed: String,

    val maize: Float,

    val cottonseedCake: Float,

    val mineralMixture: Float,

    val dryFodder: Float,

    val greenFodder: Float,

    val totalDMI: Float,

    val estimatedProtein: Float,

    val estimatedEnergy: Float,

    val dailyCost: Float

) : Serializable