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
 * AdminProfileMenu class represents the activity for the admin's profile menu.
 * It displays the admin's profile information and allows for actions such as logout.
 */
class AdminProfileMenu : AppCompatActivity() {

    // Declare UI elements
    lateinit var beTutorText : TextView
    lateinit var logout : ImageView
    lateinit var logoutText : TextView
    lateinit var pic : ImageView

    lateinit var name : TextView
    lateinit var location : TextView

    lateinit var nameText : EditText
    lateinit var emailText : EditText
    lateinit var phoneText : EditText

    lateinit var backButton : ImageButton
    lateinit var pfp : ImageView
    lateinit var logoutButton : Button

    // Entry point of the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile_menu)

        // Initialize UI elements
        backButton = findViewById(R.id.backButton)
        pfp = findViewById(R.id.PFP)
        name = findViewById(R.id.name)
        location = findViewById(R.id.address)
        emailText = findViewById(R.id.emailAddressField)
        nameText = findViewById(R.id.nameField)
        phoneText = findViewById(R.id.contactField)
        logoutButton = findViewById(R.id.logout)

        // Initialize DatabaseHelper to interact with the database
        var databaseHelper = DatabaseHandler(this)

        // Get the username from the Intent
        val intent = getIntent();
        val username = intent.getStringExtra("user")

        // Retrieve user information from the database
        val user = databaseHelper.getUser(username!!)

        // Set hints and text for UI elements based on user information
        emailText.setHint(user!!.userID)
        nameText.setHint(user!!.fullName)
        phoneText.setHint(user!!.contactNumber)
        name.setText(user!!.fullName)
        location.setText(user!!.location)

        // Decode and display the user's profile picture
        var bitmap = BitmapFactory.decodeByteArray(user.PFP,0,user.PFP!!.size)
        var scaledBitmap = Bitmap.createScaledBitmap(bitmap,150,150,true)
        pfp.setImageBitmap(scaledBitmap)


        // Set up click listeners for needed buttons
        backButton.setOnClickListener() {
            var newIntent = Intent(this,AdminMenu::class.java)
            newIntent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }

        logoutButton.setOnClickListener() {
            var newIntent = Intent(this, Login::class.java)
            startActivity(newIntent)
        }
    }
}