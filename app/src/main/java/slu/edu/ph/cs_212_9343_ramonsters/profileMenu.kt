/**
 * This class represents the profile menu activity where users can view their profile information,
 * become a tutor, and logout from the application.
 *
 * @property beTutor ImageView representing the "Become a Tutor" option.
 * @property beTutorText TextView representing the text for the "Become a Tutor" option.
 * @property logout ImageView representing the "Logout" option.
 * @property logoutText TextView representing the text for the "Logout" option.
 * @property databaseHelper DatabaseHandler instance for database operations.
 * @property pic ImageView representing the user's profile picture.
 * @property nameText TextView displaying the user's full name.
 * @property emailText TextView displaying the user's email.
 */

package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class profileMenu : AppCompatActivity() {

    lateinit var beTutor : ImageView
    lateinit var beTutorText : TextView
    lateinit var logout : ImageView
    lateinit var logoutText : TextView
    var databaseHelper = DatabaseHandler(this)
    lateinit var pic : ImageView
    lateinit var nameText : TextView
    lateinit var emailText : TextView


    /**
     * Overrides the onCreate method to initialize the activity.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_menu)

        // UI element initialization
        beTutor = findViewById(R.id.beTutorImage)
        beTutorText = findViewById(R.id.beTutorText)
        logout = findViewById(R.id.logoutButton)
        logoutText = findViewById(R.id.logoutText)
        pic = findViewById(R.id.profilePic)
        nameText = findViewById(R.id.nameText)
        emailText = findViewById(R.id.emailText)

        // Retrieving user information from the intent
        val intent = intent;
        val username = intent.getStringExtra("User")
        val user = databaseHelper.getUser(username!!)

        // Displaying user profile information
        val bitmap = BitmapFactory.decodeByteArray(user!!.PFP, 0, user!!.PFP!!.size)
        Log.i("bitmap", "Decode complete")
        pic.setImageBitmap(bitmap)
        nameText.setText(user.fullName)
        emailText.setText(user.userID)
        Log.i("pfpPic", "Set pic complete")

        // Setting up click listeners for "Become a Tutor" and "Logout" options
        beTutorText.setOnClickListener() {
            var intent = Intent(this, TutorApplication::class.java)
            intent.putExtra("User", username)
            startActivity(intent)
        }
        beTutor.setOnClickListener() {
            var intent = Intent(this, TutorApplication::class.java)
            intent.putExtra("User", username)
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