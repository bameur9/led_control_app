package com.example.led_control_app

import BluetoothViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.led_control_app.services.BluetoothService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiScreen(onBluetoothRequest: (onSuccess: () -> Unit) -> Unit, onSendWifiCredentials: (String, String) -> Unit, viewModel: BluetoothViewModel = viewModel()) {
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Effect that runs when the composable is first launched
    LaunchedEffect(Unit) {
        onBluetoothRequest {
            // Optionally, you can update the ViewModel or UI state here if needed
            viewModel.setConnectedDeviceName("ESP32_LED_Control")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wifi", style = MaterialTheme.typography.titleLarge,
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
                    .padding(top = 100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0x00003FEC))
                        )
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Wifi Screen", style = MaterialTheme.typography.titleLarge,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}



