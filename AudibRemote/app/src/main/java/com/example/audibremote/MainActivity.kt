package com.example.audibremote

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import java.io.IOException
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var selectedDevice: BluetoothDevice? = null
    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    private lateinit var txtStatus: TextView

    private val sppUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private val btPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }

    private val enableBtLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { updateStatus() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter

        txtStatus = findViewById(R.id.txtStatus)

        findViewById<Button>(R.id.btnEnableBT).setOnClickListener { enableBluetooth() }
        findViewById<Button>(R.id.btnScan).setOnClickListener { selectDeviceDialog() }
        findViewById<Button>(R.id.btnConnect).setOnClickListener { connectToSelected() }
        findViewById<Button>(R.id.btnDisconnect).setOnClickListener { disconnect() }

        // Map amplifier commands
        bindCommandButton(R.id.btnPower, "00")
        bindCommandButton(R.id.btnIN0, "05")
        bindCommandButton(R.id.btnMenu, "02")
        bindCommandButton(R.id.btnLoudness, "0C")
        bindCommandButton(R.id.btnIN1, "06")
        bindCommandButton(R.id.btnMute, "01")
        bindCommandButton(R.id.btnSurround, "0D")
        bindCommandButton(R.id.btnIN2, "07")
        bindCommandButton(R.id.btnVolUp, "03")
        bindCommandButton(R.id.btn3D, "0E")
        bindCommandButton(R.id.btnIN3, "08")
        bindCommandButton(R.id.btnVolDown, "04")
        bindCommandButton(R.id.btnToneBypass, "0F")
        bindCommandButton(R.id.btnIN4, "09")
        bindCommandButton(R.id.btnPrevIN, "0A")
        bindCommandButton(R.id.btnNextIN, "0B")
        bindCommandButton(R.id.btnFMUp, "11")
        bindCommandButton(R.id.btnFMMode, "13")
        bindCommandButton(R.id.btnRDS, "10")
        bindCommandButton(R.id.btnFMDown, "12")
        bindCommandButton(R.id.btnFMStore, "15")
        bindCommandButton(R.id.btnFMMono, "14")
        bindCommandButton(R.id.btn1, "17")
        bindCommandButton(R.id.btn2, "18")
        bindCommandButton(R.id.btn3, "19")
        bindCommandButton(R.id.btn4, "1A")
        bindCommandButton(R.id.btn5, "1B")
        bindCommandButton(R.id.btn6, "1C")
        bindCommandButton(R.id.btn7, "1D")
        bindCommandButton(R.id.btn8, "1E")
        bindCommandButton(R.id.btn9, "1F")
        bindCommandButton(R.id.btn0, "16")
        bindCommandButton(R.id.btnTime, "20")
        bindCommandButton(R.id.btnBright, "23")
        bindCommandButton(R.id.btnSpectrum, "25")
        bindCommandButton(R.id.btnAlarm, "21")
        bindCommandButton(R.id.btnDisplay, "24")
        bindCommandButton(R.id.btnFullSpeed, "26")
        bindCommandButton(R.id.btnTimer, "22")

        // Restore last device from SharedPreferences
        val lastAddress = getLastDeviceAddress()
        if (lastAddress != null && bluetoothAdapter != null) {
            val dev = bluetoothAdapter!!.getRemoteDevice(lastAddress)
            selectedDevice = dev
            reconnectToDevice()
        }

        updateStatus()
    }

    // Save last device address persistently using KTX
    private fun saveLastDeviceAddress(address: String) {
        val prefs = getSharedPreferences("audib_prefs", MODE_PRIVATE)
        prefs.edit {
            putString("last_device_address", address)
        }
    }

    private fun getLastDeviceAddress(): String? {
        val prefs = getSharedPreferences("audib_prefs", MODE_PRIVATE)
        return prefs.getString("last_device_address", null)
    }

    private fun bindCommandButton(id: Int, code: String) {
        findViewById<Button>(id).setOnClickListener { sendCommand(code) }
    }

    private fun updateStatus() {
        val bt = if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            == PackageManager.PERMISSION_GRANTED && bluetoothAdapter?.isEnabled == true) "ON" else "OFF"

        val dev = if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            == PackageManager.PERMISSION_GRANTED) {
            selectedDevice?.let { "${it.name} (${it.address})" } ?: "None"
        } else "Unknown"

        val conn = if (socket?.isConnected == true) "Connected" else "Disconnected"

        txtStatus.text = getString(R.string.status_format, bt, dev, conn)
    }

    private fun enableBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, getString(R.string.bluetooth_not_supported), Toast.LENGTH_SHORT).show()
            return
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            == PackageManager.PERMISSION_GRANTED && bluetoothAdapter?.isEnabled == true) {
            Toast.makeText(this, getString(R.string.bluetooth_already_enabled), Toast.LENGTH_SHORT).show()
            updateStatus()
            return
        }
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableBtLauncher.launch(intent)
    }

    private fun ensurePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val needsConnect = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            val needsScan = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
            if (needsConnect || needsScan) {
                btPermissionLauncher.launch(arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN
                ))
            }
        } else {
            val needsLoc = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            if (needsLoc) {
                btPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }
    }

    private fun selectDeviceDialog() {
        ensurePermissions()
        if (bluetoothAdapter == null || bluetoothAdapter?.isEnabled != true) {
            Toast.makeText(this, getString(R.string.enable_bt_first), Toast.LENGTH_SHORT).show()
            return
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.bluetooth_permission_denied), Toast.LENGTH_SHORT).show()
            return
        }

        val paired = bluetoothAdapter?.bondedDevices
        if (paired.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.pair_first), Toast.LENGTH_LONG).show()
            return
        }

        val names = paired.map { "${it.name} (${it.address})" }.toTypedArray()
        val devices = paired.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Select device")
            .setItems(names) { _, which ->
                selectedDevice = devices[which]
                Toast.makeText(this, getString(R.string.selected_device, selectedDevice?.name ?: ""), Toast.LENGTH_SHORT).show()
                updateStatus()
            }
            .show()
    }

    private fun connectToSelected() {
        ensurePermissions()
        val dev = selectedDevice
        if (dev == null) {
            Toast.makeText(this, getString(R.string.no_device_selected), Toast.LENGTH_SHORT).show()
            return
        }

        Thread {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                    runOnUiThread {
                        Toast.makeText(this, getString(R.string.bluetooth_permission_denied), Toast.LENGTH_SHORT).show()
                    }
                    return@Thread
                }

                socket = dev.createRfcommSocketToServiceRecord(sppUUID)
                bluetoothAdapter?.cancelDiscovery()
                socket?.connect()
                outputStream = socket?.outputStream

                runOnUiThread {
                    Toast.makeText(this, getString(R.string.connected_to, dev.name), Toast.LENGTH_SHORT).show()
                    saveLastDeviceAddress(dev.address) // ✅ Save persistently
                    updateStatus()
                }
            } catch (_: SecurityException) {
                runOnUiThread {
                    Toast.makeText(this, getString(R.string.missing_bluetooth_permission), Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(this, getString(R.string.connection_failed, e.message ?: ""), Toast.LENGTH_LONG).show()
                    updateStatus()
                }
                closeSocketQuietly()
                e.printStackTrace()
            }
        }.start()
    }

    // 🔄 Auto-reconnect method
    private fun reconnectToDevice() {
        if (selectedDevice == null) return
        Thread {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                    runOnUiThread {
                        Toast.makeText(this, getString(R.string.bluetooth_permission_denied), Toast.LENGTH_SHORT).show()
                    }
                    return@Thread
                }

                socket = selectedDevice!!.createRfcommSocketToServiceRecord(sppUUID)
                bluetoothAdapter?.cancelDiscovery()
                socket?.connect()
                outputStream = socket?.outputStream

                runOnUiThread {
                    Toast.makeText(this, getString(R.string.connected_to, selectedDevice!!.name), Toast.LENGTH_SHORT).show()
                    updateStatus()
                }
            } catch (_: Exception) {
                runOnUiThread {
                    Toast.makeText(this, getString(R.string.connection_failed, "Reconnect failed"), Toast.LENGTH_LONG).show()
                    updateStatus()
                }
                closeSocketQuietly()
            }
        }.start()
    }

    private fun sendCommand(code: String) {
        val os = outputStream
        if (os == null) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val fullCmd = "RC $code\r\n"
            os.write(fullCmd.toByteArray(Charsets.US_ASCII))
            os.flush()
            Toast.makeText(this, getString(R.string.sent_command, fullCmd), Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, getString(R.string.send_failed, e.message ?: ""), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun disconnect() {
        try {
            outputStream?.flush()
            outputStream?.close()
        } catch (_: Exception) {}
        closeSocketQuietly()
        outputStream = null
        updateStatus()
        Toast.makeText(this, getString(R.string.disconnected), Toast.LENGTH_SHORT).show()
    }

    private fun closeSocketQuietly() {
        try {
            socket?.close()
        } catch (_: Exception) {}
        socket = null
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
    }
}