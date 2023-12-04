package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class ViewDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)

        var databaseHandler : DatabaseHandler = DatabaseHandler(this)

        var name : TextView = findViewById(R.id.name)
        var location : TextView = findViewById(R.id.location)
        var askingPrice : TextView = findViewById(R.id.askingPriceDisplay)
        var email : TextView = findViewById(R.id.email)
        var phoneNumber : TextView = findViewById(R.id.phoneNumber)
        var specialization1 : TextView = findViewById(R.id.phoneNumberText)
        var specialization2 : TextView = findViewById(R.id.specialization2)
        var specialization3 : TextView = findViewById(R.id.specialization3)
        var viewResume : Button = findViewById(R.id.viewResume)
        var backButton : ImageButton = findViewById(R.id.backButton)

        var intent = getIntent()
        var tutor = intent.getStringExtra("tutor")
        var username = intent.getStringExtra("username")
        var tutorObj : User? = databaseHandler.getUser(tutor!!)

        var databaseHelper = DatabaseHandler(this)
        var user : User? = databaseHelper.getUser(username.toString())

        name.setText(tutorObj!!.fullName)
        location.setText(tutorObj!!.location)
        askingPrice.setText(tutorObj!!.rate.toString())
        email.setText(tutorObj!!.userID)
        phoneNumber.setText(tutorObj!!.contactNumber)
        specialization1.setText(tutorObj!!.specialization1)
        specialization2.setText(tutorObj!!.specialization2)
        specialization3.setText(tutorObj!!.specialization3)

        viewResume.setOnClickListener() {
            databaseHandler.applyToATutor(username!!,tutor)
        }

        backButton.setOnClickListener() {
            var newIntent = Intent(this,StudentMenu::class.java)
            intent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }


    }
}