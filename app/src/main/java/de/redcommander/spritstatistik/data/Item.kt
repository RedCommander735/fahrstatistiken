package de.redcommander.spritstatistik.data

import java.time.LocalDate

class Item(
    val id: Int = 0,
    val date: LocalDate,
    val distance: Double,
    val fuelUsed: Double,
    val pricePerLiter: Double,
    val priceTotal: Double,
    val literPerKilometer: Double,
)