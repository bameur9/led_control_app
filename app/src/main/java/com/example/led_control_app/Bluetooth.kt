package com.example.led_control_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.led_control_app.viewmodel.BluetoothViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothScreen(onBluetoothRequest: (onSuccess: () -> Unit) -> Unit,
                    onSendCommand: (String) -> Unit,
                    viewModel: BluetoothViewModel = viewModel()
){
    val connectedDeviceName by viewModel.connectedDeviceName.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth", style = MaterialTheme.typography.titleLarge,
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
                    .padding(top = 150.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Button(onClick = { onBluetoothRequest {viewModel.setConnectedDeviceName("ESP32_LED_Control") } }) {
                    Text(
                        "Scan and Connect to ESP32", style = MaterialTheme.typography.titleLarge,
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                connectedDeviceName?.let {
                    Text(
                        "Connected to: $it",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 15.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }

                Button(onClick = { onSendCommand("1") }) {
                    Text("Turn On LED")
                }

                Button(onClick = { onSendCommand("A") }) {
                    Text("Red Color")
                }
            }
        })
}


