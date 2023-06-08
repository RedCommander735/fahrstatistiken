package de.redcommander.spritstatistik

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.redcommander.spritstatistik.ui.theme.SpritStatistikTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpritStatistikTheme {


                val navigationItems = listOf(
                    Screen.Overview,
                    Screen.DataList,
                    Screen.Settings
                )

                val navController = rememberNavController()

                var addValueScreenActive by remember { mutableStateOf(false) }


                BottomNavigationBar(navigationItems, navController) {
                    addValueScreenActive = it
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = 20.dp, y = (-100).dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    if (!addValueScreenActive) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(25.dp))
                                .background(
                                    if (isSystemInDarkTheme()) {
                                        Color(0xFF3C3C3C)
                                    } else {
                                        Color(0xFFC3C3C3)
                                    }
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        addValueScreenActive = true
                                        navController.navigate(Screen.AddValue.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(Screen.AddValue.drawableId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(28.dp),
                                    tint = if (isSystemInDarkTheme()) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OverviewScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Overview Screen",
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
        )
    }
}

@Composable
fun DataListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "List Screen",
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
        )
    }
}

@Composable
fun AddValueScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AddValueRow(R.drawable.gas_station, "Tankfüllung in Liter") {}
        AddValueRow(R.drawable.road, "Kilometer gefahren") {}
        AddValueRow(R.drawable.euro_symbol, "Spritpreis") {}
    }
}

@Preview
@Composable
fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
    ) {

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddValueRow(
    icon: Int,
    text: String = "placeholder"
    updateValue: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .height(115.dp)
            .fillMaxWidth()
            .padding(top = 55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val iconSize = 48.dp
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize),
                tint = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        }

        TextField(
            value = "",
            label = {
                Text(text = text)
            }, onValueChange = {
                updateValue(it)
            },
            modifier = Modifier.fillMaxWidth(0.7f),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings Screen",
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<Screen>,
    navController: NavHostController,
    updateAddValueScreenVisibility: (Boolean) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(80.dp),
                containerColor = if (isSystemInDarkTheme()) {
                    Color(0xFF161616)
                } else {
                    Color.White
                },

                ) {
                // NavBarColors
                val colorValueDark = 42
                val colorValueLight = 235

                val navBarColors: NavigationBarItemColors = if (isSystemInDarkTheme()) {
                    colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.LightGray,
                        indicatorColor = Color(
                            colorValueDark,
                            colorValueDark,
                            colorValueDark
                        ),
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.LightGray
                    )
                } else {
                    colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.DarkGray,
                        indicatorColor = Color(
                            colorValueLight,
                            colorValueLight,
                            colorValueLight
                        ),
                        selectedTextColor = Color.Black,
                        unselectedTextColor = Color.DarkGray
                    )
                }


                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val iconSize = 24.dp

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(screen.drawableId),
                                contentDescription = null,
                                modifier = Modifier.size(iconSize)
                            )
                        },
                        label = { Text(stringResource(screen.stringId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            updateAddValueScreenVisibility(false)
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        colors = navBarColors
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Overview.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Overview.route) { OverviewScreen() }
            composable(Screen.DataList.route) { DataListScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.AddValue.route) { AddValueScreen() }
        }
    }
}