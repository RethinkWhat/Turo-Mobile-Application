package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

/**
 * This class represents the activity for the user profile menu.
 * Users can view and edit their profile information, log out, and navigate to the student menu or tutor application.
 */
class ProfileMenu : AppCompatActivity() {

    lateinit var beTutorText: TextView
    lateinit var logout: ImageView
    lateinit var logoutText: TextView
    lateinit var pic: ImageView

    lateinit var name: TextView
    lateinit var location: TextView

    lateinit var nameText: EditText
    lateinit var emailText: EditText
    lateinit var phoneText: EditText

    lateinit var backButton: ImageButton
    lateinit var beTutor: Button
    lateinit var pfp: ImageView
    lateinit var logoutButton: Button

    /**
     * Overrides the onCreate method to initialize the activity.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_menu)

        // UI element initialization
        backButton = findViewById(R.id.backButton)
        beTutor = findViewById(R.id.beTutor)
        pfp = findViewById(R.id.PFP)
        name = findViewById(R.id.name)
        location = findViewById(R.id.address)
        emailText = findViewById(R.id.emailAddressField)
        nameText = findViewById(R.id.nameField)
        phoneText = findViewById(R.id.contactField)
        logoutButton = findViewById(R.id.logout)

        // Database handling for retrieving user information
        var databaseHelper = DatabaseHandler(this)
        val intent = getIntent()
        val username = intent.getStringExtra("user")
        val user = databaseHelper.getUser(username!!)

        // Setting initial values for UI elements based on user information
        emailText.setHint(user!!.userID)
        nameText.setHint(user!!.fullName)
        phoneText.setHint(user!!.contactNumber)
        name.setText(user!!.fullName)
        location.setText(user!!.location)

        // Setting user profile picture
        var bitmap = BitmapFactory.decodeByteArray(user.PFP, 0, user.PFP!!.size)
        var scaledBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
        pfp.setImageBitmap(scaledBitmap)

        // Setting up click listeners for buttons
        backButton.setOnClickListener() {
            var newIntent = Intent(this, StudentMenu::class.java)
            newIntent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }

        logoutButton.setOnClickListener() {
            var newIntent = Intent(this, Login::class.java)
            startActivity(newIntent)
        }

        beTutor.setOnClickListener() {
            var newIntent = Intent(this, TutorApplication::class.java)
            newIntent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }
    }
}
