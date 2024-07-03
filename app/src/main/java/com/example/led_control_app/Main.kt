package com.example.led_control_app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onBluetoothRequest: (onSuccess: () -> Unit) -> Unit,  onSendCommand: (String) -> Unit) {
    val navController = rememberNavController()

    Scaffold(

        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { padding ->
            NavHost(navController, startDestination = "home", Modifier.padding(padding)) {
                composable("home") {HomeScreen(navController, onBluetoothRequest) }
                composable("bluetooth") { BluetoothScreen( onBluetoothRequest, onSendCommand) }
                composable("wifi") { WifiScreen() }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Build, contentDescription = "Bluetooth") },
            label = { Text("Bluetooth") },
            selected = currentRoute == "bluetooth",
            onClick = { navController.navigate("bluetooth") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Call, contentDescription = "WiFi") },
            label = { Text("WiFi") },
            selected = currentRoute == "wifi",
            onClick = { navController.navigate("wifi") }
        )
    }
}



