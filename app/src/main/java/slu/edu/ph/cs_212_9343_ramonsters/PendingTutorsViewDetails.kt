package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

/**
 * PendingTutorsViewDetails class represents the activity for viewing details of a pending tutor request.
 * It displays information about the tutor and provides options to accept or reject the tutor.
 */
class PendingTutorsViewDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_tutors_view_details)

        // Initialize DatabaseHandler to interact with the database
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        // UI elements
        val name: TextView = findViewById(R.id.name)
        val location: TextView = findViewById(R.id.location)
        val askingPrice: TextView = findViewById(R.id.askingPriceDisplay)
        val email: TextView = findViewById(R.id.email)
        val pfp: ImageView = findViewById(R.id.tutorPicture)
        val phoneNumber: TextView = findViewById(R.id.phoneNumber)
        val specialization1: TextView = findViewById(R.id.specialization1)
        val specialization2: TextView = findViewById(R.id.specialization2)
        val specialization3: TextView = findViewById(R.id.specialization3)

        val backButton: ImageButton = findViewById(R.id.backButton)
        val resumeText: TextView = findViewById(R.id.resumeText)
        val resumeImg: ImageView = findViewById(R.id.resumeImg)
        val backgroundGray: View = findViewById(R.id.backgroundGray)
        val backButton2: ImageButton = findViewById(R.id.backButton2)
        val viewResume: Button = findViewById(R.id.viewResume)
        val acceptButton: Button = findViewById(R.id.acceptButton)
        val rejectButton: Button = findViewById(R.id.rejectButton)
        val tutorAcceptedTxt: TextView = findViewById(R.id.tutorAcceptedTxt)

        // Get tutor information from the Intent
        val intent = getIntent()
        val tutor = intent.getStringExtra("tutor")
        val username = intent.getStringExtra("username")
        val tutorObj: User? = databaseHandler.getUser(tutor!!)

        // Retrieve admin user information from the database
        val databaseHelper = DatabaseHandler(this)
        val user: User? = databaseHelper.getUser(username.toString())

        // Populate UI elements with tutor information
        name.text = tutorObj!!.fullName
        location.text = tutorObj.location
        askingPrice.text = tutorObj.rate.toString()
        email.text = tutorObj.userID
        phoneNumber.text = tutorObj.contactNumber
        specialization1.text = tutorObj.specialization1
        specialization2.text = tutorObj.specialization2
        specialization3.text = tutorObj.specialization3

        // Decode and display the tutor's profile picture
        val bitmap = BitmapFactory.decodeByteArray(tutorObj.PFP, 0, tutorObj.PFP!!.size)
        pfp.setImageBitmap(bitmap)

        // Set up the resume file name and display the resume image
        val firstName: String = tutorObj.fullName.toString().split(" ")[0]
        resumeText.text = "$firstName-resume.jpg"
        val bitmap2 = BitmapFactory.decodeByteArray(tutorObj.resume, 0, tutorObj.resume!!.size)
        resumeImg.setImageBitmap(bitmap2)

        // Set up click listeners for viewing the resume
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

        // Set up click listener for the "Back" button
        backButton.setOnClickListener() {
            val newIntent = Intent(this, AdminMenu::class.java)
            newIntent.putExtra("user", username)
            startActivity(newIntent)
        }

        // Set up click listeners for accepting or rejecting the tutor
        acceptButton.setOnClickListener() {
            databaseHandler.acceptOrRejectTutor(tutorObj.userID, 1)
            tutorAcceptedTxt.visibility = View.VISIBLE
            tutorAcceptedTxt.text = "TUTOR ACCEPTED"
        }
        rejectButton.setOnClickListener() {
            databaseHandler.acceptOrRejectTutor(tutorObj.userID, 0)
            tutorAcceptedTxt.visibility = View.VISIBLE
            tutorAcceptedTxt.text = "TUTOR REJECTED"
        }
    }
}
