package de.redcommander.spritstatistik.ui.item



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.redcommander.spritstatistik.data.Item
import java.time.LocalDate

class ItemEntryViewModel: ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState = ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            distance.isNotBlank() && fuelUsed.isNotBlank() && pricePerLiter.isNotBlank() && priceTotal.isNotBlank() && literPerKilometer.isNotBlank()
        }
    }
}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val distance: String = "",
    val fuelUsed: String = "",
    val pricePerLiter: String = "",
    val priceTotal: String = "",
    val literPerKilometer: String = "",
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    date = date,
    distance = distance.toDoubleOrNull() ?: 0.0,
    fuelUsed = fuelUsed.toDoubleOrNull() ?: 0.0,
    pricePerLiter = pricePerLiter.toDoubleOrNull() ?: 0.0,
    priceTotal = priceTotal.toDoubleOrNull() ?: 0.0,
    literPerKilometer = literPerKilometer.toDoubleOrNull() ?: 0.0,
)

fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    date = date,
    distance = distance.toString(),
    fuelUsed = fuelUsed.toString(),
    pricePerLiter = pricePerLiter.toString(),
    priceTotal = priceTotal.toString(),
    literPerKilometer = literPerKilometer.toString(),
)