package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class StudentMenu : AppCompatActivity() {

    lateinit var profileRedirect : ImageView
    lateinit var helloMsg : TextView
    var databaseHelper = DatabaseHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_menu)

        profileRedirect = findViewById(R.id.profileRedirectButton)
        helloMsg = findViewById(R.id.helloMsg)
        val intent = intent;
        val username = intent.getStringExtra("User")
        helloMsg.setText("Hello, $username")



        profileRedirect.setOnClickListener() {
            val intent = Intent(this, profileMenu::class.java)
            intent.putExtra("User", username)
            startActivity(intent)
        }
    }
}