package de.redcommander.spritstatistik

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.redcommander.spritstatistik.ui.theme.SpritStatistikTheme
import timber.log.Timber

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
@Preview
fun AddValueScreen(

) {
    Timber.plant(Timber.DebugTree())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var gas by remember {
            mutableStateOf(0)
        }
        var distance by remember {
            mutableStateOf(0)
        }
        var price by remember {
            mutableStateOf(0.0)
        }

        var checkValid by remember {
            mutableStateOf(false)
        }

        var isInvalid = false

        AddValueRow(R.drawable.gas_station, stringResource(id = R.string.addValueLiters), checkValidity = checkValid, isInvalid = {_isInvalid -> isInvalid = _isInvalid}) {
            if (it.isNotEmpty()) {
                gas = it.toInt()
            }
        }
        AddValueRow(R.drawable.road, stringResource(id = R.string.addValueKilometers), checkValidity = checkValid, isInvalid = {_isInvalid -> isInvalid = _isInvalid}) {
            if (it.isNotEmpty()) {
                distance = it.toInt()
            }
        }
        AddValueRow(R.drawable.euro_symbol, stringResource(id = R.string.addValuePrice), checkValidity = checkValid, isInt = false, isInvalid = {_isInvalid ->
            isInvalid = _isInvalid
            checkValid = false
        }) {
            if (it.isNotEmpty()) {
                price = it.toDouble()
                Timber.d("Price: $price")
            }
        }


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            Button(
                onClick = {checkValid = true},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        Color(0xFF3C3C3C)
                    } else {
                        Color(0xFFEBEBEB)
                    }
                )
            ) {
                Text(
                    text = stringResource(id = R.string.addButton),
                    color = if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddValueRow(
    icon: Int,
    text: String = "placeholder",
    isInt: Boolean = true,
    checkValidity: Boolean = false,
    isInvalid: (Boolean) -> Unit,
    updateValue: (String) -> Unit
) {
    Timber.plant(Timber.DebugTree())



    var textFieldState by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    fun validate(text: String) {
        val valid = text.isEmpty()
        isError = valid
        isInvalid(valid)
    }

    if (checkValidity) {
        validate(textFieldState)
    }

    Box(
        modifier = Modifier
            .padding(top = 55.dp)
    ) {
        Row(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
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

            val textFieldColors: TextFieldColors = if (isSystemInDarkTheme()) {
                TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White,
                    containerColor = Color(0xFF3C3C3C),
                    focusedIndicatorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                )
            } else {
                TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    containerColor = Color(0xFFEBEBEB),
                    focusedIndicatorColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                )
            }

            TextField(
                value = textFieldState,
                label = {
                    Text(text = text)
                },
                onValueChange = {
                    val invalidCharCount = Regex("([^0-9,.])").findAll(it).count()
                    if (invalidCharCount == 0) {
                        validate(it)
                        if (isInt) {
                            val value = it.replace(".", "").replace(",", "")
                            textFieldState = value
                            updateValue(value)
                        } else {
                            val commaCount = Regex("([,.])").findAll(it).count()
                            if (it.length > 1 || commaCount == 0) {
                                if (commaCount <= 1) {
                                    textFieldState = it
                                    updateValue(it.replace(",", "."))
                                }
                            }
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(64.dp)
                    .onFocusChanged {
                        if (it.hasFocus) {
                            validate(textFieldState)
                        }
                    },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions { validate(textFieldState) },
                colors = textFieldColors,
                singleLine = true,
                isError = isError,
                trailingIcon = {
                    if (isError)
                        Icon(painterResource(id = R.drawable.error),"error", tint = MaterialTheme.colorScheme.error)
                }
                )

        }
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
            startDestination = Screen.AddValue.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Overview.route) { OverviewScreen() }
            composable(Screen.DataList.route) { DataListScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.AddValue.route) { AddValueScreen() }
        }
    }
}