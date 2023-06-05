package de.redcommander.spritstatistik

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val stringId: Int, @DrawableRes val drawableId: Int) {
    object Overview : Screen("overview", R.string.overview, R.drawable.gas_station)
    object DataList : Screen("dataList", R.string.list, R.drawable.list_solid)
    object Settings : Screen("settings", R.string.settings, R.drawable.settings)
    object AddValue : Screen("addValue", R.string.addValue, R.drawable.plus_solid)
}
