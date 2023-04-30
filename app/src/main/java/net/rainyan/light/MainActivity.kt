package net.rainyan.light

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.hardware.camera2.CameraManager
import android.widget.Toast
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var manager: CameraManager
    private lateinit var button1: Button
    var startUp: Boolean = true
    var cameraId: String = ""
    var cameraSw = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Developer
        button1 = findViewById(R.id.button1)
        manager = getSystemService(CAMERA_SERVICE) as CameraManager
        try {
            manager.registerTorchCallback(object : CameraManager.TorchCallback() {
                override fun onTorchModeChanged(id: String, enabled: Boolean) {
                    super.onTorchModeChanged(id, enabled)
                    if(id != "0") return
                    cameraId = id
                    cameraSw = enabled

                    if (startUp) {
                        manager.setTorchMode(cameraId, cameraSw)
                        startUp = false
                    }
                }
            }, Handler(Looper.getMainLooper()))
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

    }

    fun clickLight(@Suppress("UNUSED_PARAMETER") view: View) {
        manager.setTorchMode(cameraId, !cameraSw)
        if(!cameraSw) {
            button1.setText(R.string.light_off)
        }else{
            button1.setText(R.string.light_on)
        }
    }
}