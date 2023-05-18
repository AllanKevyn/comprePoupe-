package com.example.comprepoupe.model

import java.io.Serializable

data class Product(
    val image: Int,
    val description: String,
    val pricePrev: String,
    val price: String
): Serializable