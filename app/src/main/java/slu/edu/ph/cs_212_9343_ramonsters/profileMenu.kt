package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class profileMenu : AppCompatActivity() {

    lateinit var beTutor : ImageView
    lateinit var beTutorText : TextView
    lateinit var logout : ImageView
    lateinit var logoutText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_menu)

        beTutor = findViewById(R.id.beTutorImage)
        beTutorText = findViewById(R.id.beTutorText)
        logout = findViewById(R.id.logoutButton)
        logoutText = findViewById(R.id.logoutText)

        beTutorText.setOnClickListener() {
            var intent = Intent(this, TutorApplication::class.java)
            startActivity(intent)
        }
        beTutor.setOnClickListener() {
            var intent = Intent(this, TutorApplication::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener() {
            var intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        logoutText.setOnClickListener() {
            var intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}