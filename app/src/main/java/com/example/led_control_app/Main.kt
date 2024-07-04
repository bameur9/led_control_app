package com.example.led_control_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
            icon = { Image(painter = painterResource(id = R.drawable.home), contentDescription = "Bluetooth", modifier = Modifier.size(24.dp)) },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Image(painter = painterResource(id = R.drawable.bluetooth), contentDescription = "Bluetooth", modifier = Modifier.size(24.dp)) },
            label = { Text("Bluetooth") },
            selected = currentRoute == "bluetooth",
            onClick = { navController.navigate("bluetooth") }
        )
        NavigationBarItem(
            icon = { Image(painter = painterResource(id = R.drawable.wifi), contentDescription = "Wifi", modifier = Modifier.size(24.dp)) },
            label = { Text("WiFi") },
            selected = currentRoute == "wifi",
            onClick = { navController.navigate("wifi") }
        )
    }
}



