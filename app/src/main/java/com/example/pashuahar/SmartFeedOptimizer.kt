package com.example.pashuahar

/**
 * Utility class to provide smart suggestions for cheaper feed alternatives.
 */
object SmartFeedOptimizer {

    fun getOptimizedSuggestions(recipe: FeedRecipe): List<OptimizationSuggestion> {
        val suggestions = mutableListOf<OptimizationSuggestion>()

        // 1. Suggest Broken Rice as alternative to Maize
        // recipe.maize is Float, originalQuantity expects Double. estimatedSavings needs Double calculation.
        suggestions.add(
            OptimizationSuggestion(
                originalIngredient = "Maize (Energy)",
                suggestedAlternative = "Broken Rice",
                originalQuantity = recipe.maize.toDouble(),
                estimatedSavings = recipe.maize.toDouble() * 7.0,
                nutritionBenefit = "Maintains high energy levels while reducing costs by ~25%."
            )
        )

        // 2. Suggest Groundnut Cake as alternative to Cottonseed Cake
        suggestions.add(
            OptimizationSuggestion(
                originalIngredient = "Cottonseed Cake (Protein)",
                suggestedAlternative = "Groundnut Cake",
                originalQuantity = recipe.cottonseedCake.toDouble(),
                estimatedSavings = recipe.cottonseedCake.toDouble() * 5.0,
                nutritionBenefit = "Rich in bypass protein. Improves milk fat and protein content."
            )
        )

        // 3. Suggest Green Fodder as alternative to Dry Fodder
        // 'weight' is not in FeedRecipe. We use 'totalDMI' or 'dryFodder' as a logical base.
        // Assuming we suggest optimizing 20% of the existing Dry Fodder.
        val dryFodderReplacementAmount = recipe.dryFodder.toDouble() * 0.2
        suggestions.add(
            OptimizationSuggestion(
                originalIngredient = "Expensive Dry Fodder",
                suggestedAlternative = "Seasonal Green Fodder",
                originalQuantity = dryFodderReplacementAmount,
                estimatedSavings = dryFodderReplacementAmount * 4.0,
                nutritionBenefit = "Increases palatability and Vitamin A intake. Boosts cow health."
            )
        )

        return suggestions
    }

    fun calculateTotalPotentialSavings(suggestions: List<OptimizationSuggestion>): Double {
        return suggestions.sumOf { it.estimatedSavings }
    }
}