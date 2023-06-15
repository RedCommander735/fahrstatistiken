package de.redcommander.spritstatistik.ui.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.redcommander.spritstatistik.data.Item
import java.time.format.DateTimeFormatter

class OverviewViewModel : ViewModel() {

    var overviewUiState by mutableStateOf(OverviewUiState())
        private set

    fun updateUiState(overviewDetails: OverviewDetails) {
        overviewUiState = OverviewUiState(content = overviewDetails)
    }

}

data class OverviewUiState(
    val content: OverviewDetails = OverviewDetails(),
)

data class OverviewDetails(
    val date: String = "",
    val distance: String = "",
    val fuelUsed: String = "",
    val pricePerLiter: String = "",
    val priceTotal: String = "",
    val literPerKilometer: String = "",
)

fun Item.toOverviewDetails(): OverviewDetails = OverviewDetails(
    date = date.format(DateTimeFormatter.ofPattern("dd.MMMM.yyyy")),
    distance = distance.toString(),
    fuelUsed = fuelUsed.toString(),
    pricePerLiter = pricePerLiter.toString(),
    priceTotal = priceTotal.toString(),
    literPerKilometer = literPerKilometer.toString(),
)