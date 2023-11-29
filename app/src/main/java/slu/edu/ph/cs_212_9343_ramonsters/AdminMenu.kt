package slu.edu.ph.cs_212_9343_ramonsters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AdminMenu : AppCompatActivity() {

    lateinit var names : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        /**
         * When needing information from the database this DatabaseHandler obj is what you will use.
         * Pertinent data for this menu is the list of pending tutors
         */
        var databaseHelper = DatabaseHandler(this)

        // This variable already holds all of the pending tutors in the database
        var pendingTutors : ArrayList<User> = databaseHelper.getUsers(2)


        // Sample Run to check if code words only
        var users : String = " "
        names = findViewById(R.id.names)
        for (i in pendingTutors) {
            users += i.userID
        }
        names.setText(users)
    }
}