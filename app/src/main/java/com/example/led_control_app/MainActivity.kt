package com.example.led_control_app

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.led_control_app.services.BluetoothService
import com.example.led_control_app.ui.theme.Led_Control_AppTheme

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : ComponentActivity() {

    private lateinit var bluetoothService: BluetoothService

    private val requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            bluetoothService.startScan { deviceName ->
                setConnectedDeviceName(deviceName)
            }
        }
    }

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            enableBluetoothAndScan()
        } else {
            // Handle the case where permissions are not granted
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bluetoothService = BluetoothService(this)
        enableEdgeToEdge()
        setContent {
            Led_Control_AppTheme {
                MainScreen(
                    onBluetoothRequest = { onSuccess ->
                        if (checkPermissions()) {
                            enableBluetoothAndScan()
                            onSuccess()
                        } else {
                            requestBluetoothPermissions(onSuccess)
                        }
                    },
                    onSendCommand = { command ->
                        bluetoothService.sendCommand(command)
                    }
                )
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestBluetoothPermissions(onSuccess: () -> Unit) {
        requestPermissions.launch(arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))
        if (checkPermissions()) {
            enableBluetoothAndScan()
            onSuccess()
        }
    }

    private fun enableBluetoothAndScan() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Handle the scenario where the device doesn't support Bluetooth
        } else {
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                requestBluetooth.launch(enableBtIntent)
            } else {
                // Start scanning and handle results via ViewModel
                bluetoothService.startScan { deviceName ->
                    bluetoothViewModel.setConnectedDeviceName(deviceName) // Use ViewModel here
                }
            }
        }
    }
}