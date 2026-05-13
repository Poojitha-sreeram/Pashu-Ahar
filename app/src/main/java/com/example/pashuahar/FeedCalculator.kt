package com.example.pashuahar

/**
 * FeedCalculator
 * Advanced cattle feed calculation with
 * protein and energy estimation.
 */
object FeedCalculator {

    fun calculateFeed(
        breed: String,
        weight: Float,
        milkYield: Float
    ): FeedRecipe {

        /**
         * Dry Matter Intake (DMI)
         */
        val totalDMI =
            (weight * 0.03f) + (milkYield * 0.4f)

        /**
         * Default feed ratios
         */
        var maizeRatio = 0.35f
        var cottonseedRatio = 0.25f
        var mineralRatio = 0.05f
        var dryFodderRatio = 0.20f
        var greenFodderRatio = 0.15f

        /**
         * Nutrition values
         */
        var estimatedProtein = 14f
        var estimatedEnergy = 2.5f

        /**
         * Breed-specific adjustments
         */
        when (breed.lowercase()) {

            "jersey" -> {

                maizeRatio = 0.35f
                cottonseedRatio = 0.30f
                mineralRatio = 0.05f
                dryFodderRatio = 0.15f
                greenFodderRatio = 0.15f

                estimatedProtein = 18f
                estimatedEnergy = 2.8f
            }

            "desi" -> {

                maizeRatio = 0.30f
                cottonseedRatio = 0.20f
                mineralRatio = 0.05f
                dryFodderRatio = 0.25f
                greenFodderRatio = 0.20f

                estimatedProtein = 14f
                estimatedEnergy = 2.2f
            }

            "holstein friesian" -> {

                maizeRatio = 0.40f
                cottonseedRatio = 0.30f
                mineralRatio = 0.08f
                dryFodderRatio = 0.12f
                greenFodderRatio = 0.10f

                estimatedProtein = 20f
                estimatedEnergy = 3.2f
            }
        }

        /**
         * Feed quantities
         */
        val maize =
            totalDMI * maizeRatio

        val cottonseedCake =
            totalDMI * cottonseedRatio

        val mineralMixture =
            totalDMI * mineralRatio

        val dryFodder =
            totalDMI * dryFodderRatio

        val greenFodder =
            totalDMI * greenFodderRatio

        /**
         * Homemade feed cost estimation
         */
        val dailyCost =
            (maize * 18f) +
                    (cottonseedCake * 28f) +
                    (dryFodder * 8f) +
                    (greenFodder * 6f) +
                    (mineralMixture * 40f)

        /**
         * Return FeedRecipe
         */
        return FeedRecipe(

            breed = breed,

            maize = maize,

            cottonseedCake = cottonseedCake,

            mineralMixture = mineralMixture,

            dryFodder = dryFodder,

            greenFodder = greenFodder,

            totalDMI = totalDMI,

            estimatedProtein = estimatedProtein,

            estimatedEnergy = estimatedEnergy,

            dailyCost = dailyCost
        )
    }
}