package com.example.led_control_app

import android.annotation.SuppressLint
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, onBluetoothRequest: (onSuccess: () -> Unit) -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home", style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp,
                    color =  MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
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

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "LED CONTROL APP",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(100.dp))

                        Text(
                            text = "Connect via ",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp

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
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                                modifier = Modifier.width(160.dp)
                            ) {
                                Text(
                                    text = "BLUETOOTH", color = Color.White,
                                    style = MaterialTheme.typography.titleLarge, fontSize = 16.sp,
                                )
                            }
                            Button(
                                onClick = { navController.navigate("wifi") },
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
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