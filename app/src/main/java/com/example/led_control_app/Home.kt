package com.example.led_control_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, onBluetoothRequest: (onSuccess: () -> Unit) -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home", style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center)
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0x00003FEC))
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Willkommen bei der App, mit der Sie Ihre LEDs aus der Ferne steuern können",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Comment voulez-vous le contrôler ?",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center

                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    onBluetoothRequest {
                                        navController.navigate("bluetooth")
                                    }
                                },
                                modifier = Modifier.width(160.dp)
                            ) {
                                Text(
                                    text = "BLUETOOTH", color = Color.White,
                                    style = MaterialTheme.typography.titleLarge, fontSize = 16.sp,
                                )
                            }
                            Button(
                                onClick = { navController.navigate("wifi") },
                                modifier = Modifier.width(160.dp)
                            ) {
                                Text(
                                    text = "WIFI", color = Color.White,
                                    style = MaterialTheme.typography.titleLarge, fontSize = 16.sp
                                )
                            }
                        }
                    }

                }
            }
        })
}