package ca.dheri.native_cpp_test

import android.R
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ca.dheri.native_cpp_test.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


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

        try {
            val process = Runtime.getRuntime().exec("logcat -d")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.inputStream)
            )
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                binding.logViewTextView.append( "\n"+ line)
            }

        } catch (e: IOException) {
            binding.logViewTextView.append(e.stackTraceToString())
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        binding.scrollView2.postDelayed({ binding.scrollView2.fullScroll(View.FOCUS_DOWN) }, 200)

        val touchCoordinates: String = "x=%f \t y=%f \n".format(event?.x, event?.y)
        binding.logViewTextView.append(touchCoordinates)
//        Log.d("TouchEvent", touchCoordinates)

        return true

    }

    private fun onClearLogButtonClick(): (v: View) -> Unit = {
        //    Toast.makeText(context, "B1 clicked", Toast.LENGTH_SHORT).show()
        Log.d("BUTTONS", "User tapped the %s".format(binding.clearLogButton.text))
        binding.logViewTextView.text = ""
    }
    private fun onMagicButtonClick(): (v: View) -> Unit = {
        val audioManager =  it.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val devices   = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (device in devices) {
           val  deviceInfo :String = "%s \n%s\n --\n".format(device.productName.toString(), device.audioProfiles )
            Log.d("AudioDeviceInfo", deviceInfo)
        }
        val s =  test2()
        Log.d("onMagicButtonClick test2", s)


    }
    /**
     * A native method that is implemented by the 'native_cpp_test' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String
    private external fun test2(): String

    companion object {
        init {
            System.loadLibrary("native_cpp_test")
        }
    }

}