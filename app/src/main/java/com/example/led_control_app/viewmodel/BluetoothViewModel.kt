import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.led_control_app.services.BluetoothService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class BluetoothViewModel(application: Application): AndroidViewModel(application) {
    private val bluetoothService = BluetoothService(application)
    private val _isLEDon = MutableStateFlow(false)
    val isLEDon: StateFlow<Boolean> = _isLEDon.asStateFlow()

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val job = viewModelScope.launch(Dispatchers.IO) {
        while (isActive) { // Keep running while the ViewModel is active
            _isConnected.postValue(bluetoothService.checkConnectionStatus())
            delay(5000) // Check every 5 seconds
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel() // Cancel the coroutine when ViewModel is cleared
    }


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

    fun sendWifiCredentials(ssid: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothService.sendWifiCredentials(ssid, password)
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
