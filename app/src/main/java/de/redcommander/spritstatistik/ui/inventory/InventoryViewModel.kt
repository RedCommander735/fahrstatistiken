package de.redcommander.spritstatistik.ui.inventory

import androidx.lifecycle.ViewModel
import de.redcommander.spritstatistik.data.Item

class InventoryViewModel : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class InventoryUiState(val itemList: List<Item> = listOf())