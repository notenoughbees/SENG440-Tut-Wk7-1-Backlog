package nz.ac.uclive.dsi61.backlog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FOO", "PictureActivity created!")
        setContentView(R.layout.activity_picture)
    }

    override fun onStart() {
        super.onStart()
        Log.d("FOO", "PictureActivity started!")
    }

}

