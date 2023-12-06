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

class profileMenu : AppCompatActivity() {

    lateinit var beTutorText : TextView
    lateinit var logout : ImageView
    lateinit var logoutText : TextView
    var databaseHelper = DatabaseHandler(this)
    lateinit var pic : ImageView

    lateinit var name : TextView
    lateinit var location : TextView

    lateinit var nameText : EditText
    lateinit var emailText : EditText
    lateinit var phoneText : EditText

    lateinit var backButton : ImageButton
    lateinit var beTutor : Button
    lateinit var pfp : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_menu)

        backButton = findViewById(R.id.backButton)
        beTutor = findViewById(R.id.beTutor)
        pfp = findViewById(R.id.PFP)

        name = findViewById(R.id.name)
        location = findViewById(R.id.address)

        emailText = findViewById(R.id.emailAddressField)
        nameText = findViewById(R.id.nameField)
        phoneText = findViewById(R.id.contactField)

        val intent = intent;
        val username = intent.getStringExtra("user")
        val user = databaseHelper.getUser(username!!)

        emailText.setHint(user!!.userID)
        nameText.setHint(user!!.fullName)
        phoneText.setHint(user!!.contactNumber)
        name.setText(user!!.userID)
        location.setText(user!!.location)

        var bitmap = BitmapFactory.decodeByteArray(user.PFP,0,user.PFP!!.size)
        var scaledBitmap = Bitmap.createScaledBitmap(bitmap,150,150,true)
        pfp.setImageBitmap(scaledBitmap)

        backButton.setOnClickListener() {
            var newIntent = Intent(this,StudentMenu::class.java)
            intent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }

        beTutor.setOnClickListener() {
            var newIntent = Intent(this,TutorApplication::class.java)
            intent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }

        /*beTutor = findViewById(R.id.beTutorImage)
        beTutorText = findViewById(R.id.beTutorText)
        logout = findViewById(R.id.logoutButton)
        logoutText = findViewById(R.id.logoutText)
        pic = findViewById(R.id.profilePic)
        nameText = findViewById(R.id.nameText)
        emailText = findViewById(R.id.emailText)

        val intent = intent;
        val username = intent.getStringExtra("User")
        val user = databaseHelper.getUser(username!!)

        val bitmap = BitmapFactory.decodeByteArray(user!!.PFP, 0, user!!.PFP!!.size)
        Log.i("bitmap", "Decode complete")
        pic.setImageBitmap(bitmap)
        nameText.setText(user.fullName)
        emailText.setText(user.userID)
        Log.i("pfpPic", "Set pic complete")

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

         */
    }
}