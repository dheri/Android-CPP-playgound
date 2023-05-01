package ca.dheri.native_cpp_test

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ca.dheri.native_cpp_test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.clearLogButton.setOnClickListener(onClearLogButtonClick())
        binding.magicButton.setOnClickListener(onMagicButtonClick())

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        binding.scrollView2.postDelayed({ binding.scrollView2.fullScroll(View.FOCUS_DOWN) }, 200)

        val touchCoordinates: String = "x=%f \t y=%f \n".format(event?.x, event?.y)
        binding.logViewTextView.append(touchCoordinates)
        Log.d("TouchEvent", touchCoordinates)

        return true

    }

    private fun onClearLogButtonClick(): (v: View) -> Unit = {
        //    Toast.makeText(context, "B1 clicked", Toast.LENGTH_SHORT).show()
        Log.d("BUTTONS", "User tapped the %s".format(binding.clearLogButton.text))
        binding.logViewTextView.text = ""
    }
    private fun onMagicButtonClick(): (v: View) -> Unit = {
        binding.clearLogButton.performClick()
        val audioManager =  it.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val devices   = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (device in devices) {
           val  deviceInfo :String = "%s \n%s\n --\n".format(device.productName.toString(), device.audioProfiles )
            Log.d("AudioDeviceInfo", deviceInfo)
            binding.logViewTextView.append(deviceInfo)
        }
    }
    /**
     * A native method that is implemented by the 'native_cpp_test' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("native_cpp_test")
        }
    }

}