package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class StudentMenu : AppCompatActivity() {

    lateinit var profileRedirect : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_menu)

        profileRedirect = findViewById(R.id.profileRedirectButton)

        profileRedirect.setOnClickListener() {
            val intent = Intent(this, profileMenu::class.java)
            startActivity(intent)
        }
    }
}