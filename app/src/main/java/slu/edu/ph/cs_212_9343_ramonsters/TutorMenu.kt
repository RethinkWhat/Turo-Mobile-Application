package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TutorMenu : AppCompatActivity() {


    /**
     * When needing information from the database this DatabaseHandler obj is what you will use.
     * Pertinent data for this menu is the list of pending tutors
     */
    var databaseHelper = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_menu)
    }
}