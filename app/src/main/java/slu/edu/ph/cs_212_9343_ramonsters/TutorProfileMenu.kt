package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

/**
 * Displays the attributes of the tutor in detail.
 */
class TutorProfileMenu : AppCompatActivity() {

    lateinit var logout : ImageView
    lateinit var logoutText : TextView
    lateinit var pic : ImageView

    lateinit var name : TextView
    lateinit var location : TextView

    lateinit var nameText : EditText
    lateinit var emailText : EditText
    lateinit var phoneText : EditText

    lateinit var specialization1 : EditText
    lateinit var specialization2 : EditText
    lateinit var specialization3 : EditText

    lateinit var backButton : ImageButton
    lateinit var pfp : ImageView
    lateinit var logoutButton : Button

    /**
     * Instantiates and populates the components and elements of the UI.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile_menu)

        pfp = findViewById(R.id.PFP)
        name = findViewById(R.id.name)
        location = findViewById(R.id.address)
        emailText = findViewById(R.id.emailAddressField)
        nameText = findViewById(R.id.nameField)
        phoneText = findViewById(R.id.contactField)
        backButton = findViewById(R.id.backButton)
        specialization1 = findViewById(R.id.specialization1)
        specialization2 = findViewById(R.id.specialization2)
        specialization3 = findViewById(R.id.specialization3)
        logoutButton = findViewById(R.id.logout)

        /**
         * Object of DatabaseHandler to use database operations."
         */
        var databaseHelper = DatabaseHandler(this)
        val intent = getIntent();
        val username = intent.getStringExtra("user")
        val user = databaseHelper.getUser(username!!)

        emailText.setHint(user!!.userID)
        nameText.setHint(user!!.fullName)
        phoneText.setHint(user!!.contactNumber)
        name.setText(user!!.fullName)
        location.setText(user!!.location)
        specialization1.setText(user!!.specialization1)
        specialization2.setText(user!!.specialization2)
        specialization3.setText(user!!.specialization3)

        var bitmap = BitmapFactory.decodeByteArray(user.PFP,0,user.PFP!!.size)
        var scaledBitmap = Bitmap.createScaledBitmap(bitmap,150,150,true)
        pfp.setImageBitmap(scaledBitmap)


        backButton.setOnClickListener() {
            var newIntent = Intent(this,TutorMenu::class.java)
            newIntent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }

        logoutButton.setOnClickListener() {
            var newIntent = Intent(this, Login::class.java)
            startActivity(newIntent)
        }
    }
}