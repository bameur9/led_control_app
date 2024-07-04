import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.led_control_app.services.BluetoothService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BluetoothViewModel(application: Application): AndroidViewModel(application) {
    private val bluetoothService = BluetoothService(application)
    private val _isLEDon = MutableStateFlow(false)
    val isLEDon: StateFlow<Boolean> = _isLEDon.asStateFlow()

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName: StateFlow<String?> = _connectedDeviceName.asStateFlow()

    fun setConnectedDeviceName(name: String?) {
        _connectedDeviceName.value = name
    }

    fun toggleLED() {
        val newState = !_isLEDon.value
        _isLEDon.value = newState
        sendCommand(if (newState) "1" else "0")
    }

    fun setLEDState(state: Boolean) {
        _isLEDon.value = state
    }

    fun connectToDevice() {
        CoroutineScope(Dispatchers.IO).launch {
            bluetoothService.startScan { deviceName ->
                setConnectedDeviceName(deviceName.toString())
            }
        }
    }

    fun sendCommand(command: String) {
        CoroutineScope(Dispatchers.IO).launch {
            bluetoothService.sendCommand(command)
        }
    }

    fun initiateDeviceScan() {
        CoroutineScope(Dispatchers.IO).launch {
            bluetoothService.startScan { deviceName ->
                _connectedDeviceName.value = deviceName.toString() // Directly set the value
            }
        }
    }

}
