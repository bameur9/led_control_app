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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.led_control_app.viewmodel.BluetoothViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothScreen(onBluetoothRequest: (onSuccess: () -> Unit) -> Unit,
                    onSendCommand: (String) -> Unit,
                    viewModel: BluetoothViewModel = viewModel()
){
    val connectedDeviceName by viewModel.connectedDeviceName.collectAsState()
    val isLEDon by viewModel.isLEDon.collectAsState()  // Tracks the state of the LED

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth", style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface,
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
                    )
                ,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    onClick = { onBluetoothRequest {viewModel.setConnectedDeviceName("ESP32_LED_Control") } }) {
                    Text(
                        "Connect to ESP32", style = MaterialTheme.typography.titleLarge,
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row (modifier = Modifier.padding(horizontal = 10.dp)){
                    Text(
                        "Connected to:",
                        color = Color.Blue,
                        fontSize = 20.sp,
                    )

                    if (connectedDeviceName.isNullOrEmpty()) {
                        Text(
                            "No device connected",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            color = Color.Red,
                        )
                    } else {
                        Text(
                            "$connectedDeviceName",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            color = Color.Red,
                        )
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                // Toggle button for the LED
                Surface (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                ){
                    Switch(
                        checked = isLEDon,
                        onCheckedChange = {
                            viewModel.setLEDState(it)
                            onSendCommand(if (it) "1" else "0")  // Send command based on the toggle state
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Black,
                            checkedTrackColor = Color.LightGray,
                            uncheckedTrackColor = Color.DarkGray
                        )
                    )

                    Text(
                        text = if (isLEDon) "ON" else "OFF",
                        fontSize = 16.sp,
                        color = if (isLEDon) Color.Green else Color.Black,
                    )
                }


            }
        })
}




