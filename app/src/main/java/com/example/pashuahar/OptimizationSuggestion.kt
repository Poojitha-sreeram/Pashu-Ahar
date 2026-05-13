package com.example.pashuahar

import java.io.Serializable

/**
 * Data class representing a suggested feed alternative.
 */
data class OptimizationSuggestion(
    val originalIngredient: String,
    val suggestedAlternative: String,
    val originalQuantity: Double,
    val estimatedSavings: Double,
    val nutritionBenefit: String
) : Serializable