package com.example.led_control_app.services

import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.UUID


class BluetoothService(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    private var bluetoothDevice: BluetoothDevice? = null
    private var bluetoothSocket: BluetoothSocket? = null

    private var isConnected: Boolean = false // Track connection status

    fun startScan(param: (Any) -> Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.BLUETOOTH_SCAN), PERMISSION_REQUEST_CODE
            )
            return
        }

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            if (device.name == "ESP32_LED_Control") {
                bluetoothDevice = device
                connectToDevice(device)
                return@forEach
            }
        }
    }

    fun startScanBluetooth() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.BLUETOOTH_SCAN), PERMISSION_REQUEST_CODE
            )
            return
        }

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            if (device.name == "ESP32_LED_Control") {
                bluetoothDevice = device
                connectToDevice(device)
                return@forEach
            }
        }
    }



    private fun connectToDevice(device: BluetoothDevice) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), PERMISSION_REQUEST_CODE
            )
            return
        }

        try {
            Log.d(TAG, "Attempting to create and connect Bluetooth socket")
            // Utilisez un socket RFCOMM non sécurisé pour éviter les problèmes d'authentification
            val socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID)
            socket.connect()
            bluetoothSocket = socket
            isConnected = true
            Log.d(TAG, "Connected to ${device.name}")
        } catch (e: IOException) {
            Log.e(TAG, "Could not connect to device", e)
            try {
                Log.d(TAG, "Trying fallback...")
                val clazz = device.javaClass
                val paramTypes = arrayOf<Class<*>>(Integer.TYPE)
                val m = clazz.getMethod("createRfcommSocket", *paramTypes)
                val fallbackSocket = m.invoke(device, 1) as BluetoothSocket
                fallbackSocket.connect()
                isConnected = true // Update connection status on fallback success
                bluetoothSocket = fallbackSocket
                Log.d(TAG, "Connected to ${device.name} with fallback")
            } catch (e2: Exception) {
                isConnected = false // Update connection status on failure
                Log.e(TAG, "Couldn't establish Bluetooth connection!", e2)
            }
        }
    }

    fun sendCommand(command: String) {
        Log.d(TAG, "Attempting to send command: $command")
        val socket = bluetoothSocket
        if (socket == null) {
            Log.e(TAG, "BluetoothSocket is null, cannot send command.")
            return
        }

        try {
            socket.outputStream.write(command.toByteArray())
            Log.d(TAG, "Command sent: $command")
        } catch (e: IOException) {
            Log.e(TAG, "Error sending command", e)
        }
    }


    fun sendWifiCredentials(ssid: String, password: String) {
        val credentials = "$ssid,$password"
        sendCommand(credentials)
    }

    fun checkConnectionStatus(): Boolean {
        // Check if the socket is connected and update the status
        return bluetoothSocket?.isConnected ?: false && isConnected
    }




    companion object {
        private const val TAG = "BluetoothService"
        private const val PERMISSION_REQUEST_CODE = 1
        private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard UUID for SPP
    }
}