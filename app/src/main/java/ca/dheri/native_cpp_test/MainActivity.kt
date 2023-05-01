package ca.dheri.native_cpp_test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import ca.dheri.native_cpp_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.clearLogButton.setOnClickListener(clearLogViewTextView())

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
    private fun clearLogViewTextView(): (v: View) -> Unit = {
        //    Toast.makeText(context, "B1 clicked", Toast.LENGTH_SHORT).show()
        Log.d("BUTTONS", "User tapped the %s".format(binding.clearLogButton.text))
        binding.logViewTextView.text = ""
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