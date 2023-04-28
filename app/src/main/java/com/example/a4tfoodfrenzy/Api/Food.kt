package com.example.a4tfoodfrenzy.Api

data class Food(
    val name: String,
    val calories: Double,
    val serving_size_g: Int,
    val fat_total_g: Double,
    val fat_saturated_g: Double,
    val protein_g: Double,
    val sodium_mg: Int,
    val potassium_mg: Int,
    val cholesterol_mg: Int,
    val carbohydrates_total_g: Double,
    val fiber_g: Double,
    val sugar_g: Double
)
