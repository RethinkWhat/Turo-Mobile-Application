package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/**
 * This class represents the activity for viewing details of a tutor.
 * Users can see information about the tutor, such as name, location, asking price, contact details,
 * specializations, and view the tutor's resume. Users can also send a tutoring request to the tutor.
 */
class ViewDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)

        // Database handler for interacting with user data
        var databaseHandler = DatabaseHandler(this)

        // UI element initialization
        var name : TextView = findViewById(R.id.name)
        var location : TextView = findViewById(R.id.location)
        var askingPrice : TextView = findViewById(R.id.askingPriceDisplay)
        var email : TextView = findViewById(R.id.email)
        var phoneNumber : TextView = findViewById(R.id.phoneNumber)
        var specialization1 : TextView = findViewById(R.id.specialization1)
        var specialization2 : TextView = findViewById(R.id.specialization2)
        var specialization3 : TextView = findViewById(R.id.specialization3)
        var tutorPicture : ImageView = findViewById(R.id.tutorPicture)
        var viewResume : Button = findViewById(R.id.viewResume)
        var requestSentText : TextView = findViewById(R.id.requestSentText)

        var backButton : ImageButton = findViewById(R.id.backButton)
        var resumeText : TextView = findViewById(R.id.resumeText)
        var resumeImg : ImageView = findViewById(R.id.resumeImg)
        var backgroundGray : View = findViewById(R.id.backgroundGray)
        var backButton2 : ImageButton = findViewById(R.id.backButton2)

        var bookButton : Button = findViewById(R.id.acceptButton)

        // Get data from the intent
        var intent = getIntent()
        var tutor = intent.getStringExtra("tutor")
        var username = intent.getStringExtra("user")
        var user = databaseHandler.getUser(username!!)
        var tutorObj : User? = databaseHandler.getUser(tutor!!)

        // Check if the user has already sent a request or the tutor is booked
        if (user!!.pendings.toString().contains(tutorObj!!.userID)) {
            requestSentText.visibility = View.VISIBLE
            bookButton.visibility = View.GONE
        }
        if (user!!.confirmations.toString().contains(tutorObj!!.userID)) {
            requestSentText.visibility = View.VISIBLE
            requestSentText.setText("TUTOR BOOKED")
            bookButton.visibility = View.GONE
        }

        // Set tutor details in UI
        name.setText(tutorObj!!.fullName)
        location.setText(tutorObj!!.location)
        askingPrice.setText(tutorObj!!.rate.toString())
        email.setText(tutorObj!!.userID)
        phoneNumber.setText(tutorObj!!.contactNumber)
        specialization1.setText(tutorObj!!.specialization1)
        specialization2.setText(tutorObj!!.specialization2)
        specialization3.setText(tutorObj!!.specialization3)
        // Set up UI for viewing the tutor's resume
        var firstName : String = tutorObj.fullName.toString().split(" ")[0]
        resumeText.setText("$firstName-resume.jpg")

        // Set tutor's profile picture
        var bitmap = BitmapFactory.decodeByteArray(tutorObj.resume,0,tutorObj.resume!!.size)
        resumeImg.setImageBitmap(bitmap)

        var bitmap2 = BitmapFactory.decodeByteArray(tutorObj.PFP,0,tutorObj.PFP!!.size)
        tutorPicture.setImageBitmap(bitmap2)

        // Set up click listeners for buttons
        viewResume.setOnClickListener() {
            resumeImg.visibility = View.VISIBLE
            backgroundGray.visibility = View.VISIBLE
            backButton2.visibility = View.VISIBLE
        }
        backButton2.setOnClickListener() {
            resumeImg.visibility = View.GONE
            backgroundGray.visibility = View.GONE
            backButton2.visibility = View.GONE
        }

        backButton.setOnClickListener() {
            var newIntent = Intent(this,StudentMenu::class.java)
            newIntent.putExtra("user", username)
            startActivity(newIntent)
        }
        bookButton.setOnClickListener() {
            // Send a tutoring request to the tutor
            databaseHandler.applyToATutor(username!!,tutor)
            bookButton.visibility = View.GONE
            requestSentText.visibility = View.VISIBLE
        }


        // Set user's profile picture for redirection
        var bitmap3 = BitmapFactory.decodeByteArray(user!!.PFP,0,user.PFP!!.size)
        var scaledBitMap = Bitmap.createScaledBitmap(bitmap3,
            100,
            100, true)
        var profileRedirectButton: Button = findViewById(R.id.profileRedirectButton)
        var imageOfRedirectButton : ImageView = findViewById(R.id.profileRedirectImage)
        imageOfRedirectButton.setImageBitmap(scaledBitMap)
        profileRedirectButton.setOnClickListener() {
            // Redirect to the user's profile menu
            val intent = Intent(this, ProfileMenu::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
        }


    }
}