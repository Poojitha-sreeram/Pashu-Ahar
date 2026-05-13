package com.example.pashuahar

import java.io.Serializable

/**
 * Enhanced data class representing a saved feed report with complete metrics.
 */
data class FeedHistoryItem(
    val id: Long = System.currentTimeMillis(),
    val breed: String,
    val age: String,
    val weight: Double,
    val milkYield: Double,
    val totalFeed: Double,
    val maize: Double,
    val cottonseedCake: Double,
    val mineralMix: Double,
    val homemadeCost: Double,
    val marketCost: Double,
    val savings: Double,
    val timestamp: Long
) : Serializable
