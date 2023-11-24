package slu.edu.ph.cs_212_9343_ramonsters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class TutorApplication : AppCompatActivity() {

    lateinit var imageButton : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_application)

        imageButton = findViewById(R.id.uploadImageButton)
    }
}