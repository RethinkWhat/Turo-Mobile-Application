package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class StudentMenu : AppCompatActivity() {

    lateinit var profileRedirect : ImageView
    lateinit var helloMsg : TextView

    /**
     * When needing information from the database this DatabaseHandler obj is what you will use.
     * Pertinent data for this menu is the list of pending tutors
     */
    var databaseHelper = DatabaseHandler(this)

    // This variable already holds all of the pending tutors in the database
    var possibleTutors : ArrayList<User> = databaseHelper.getUsers(1)

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