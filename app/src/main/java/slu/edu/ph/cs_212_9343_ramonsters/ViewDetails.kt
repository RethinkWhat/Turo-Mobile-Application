/**
 * This class represents the activity for viewing details of a tutor.
 * It displays information about the tutor, such as name, location, asking price, contact details,
 * and specializations. Users can also view the tutor's resume and go back to the student menu.
 */

package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class ViewDetails : AppCompatActivity() {

    /**
     * Overrides the onCreate method to initialize the activity.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)


        // Database handling for retrieving tutor and user information
        var databaseHandler : DatabaseHandler = DatabaseHandler(this)

        // UI element initialization
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

        // Retrieving tutor and user information from the intent
        var intent = getIntent()
        var tutor = intent.getStringExtra("tutor")
        var username = intent.getStringExtra("username")
        var tutorObj : User? = databaseHandler.getUser(tutor!!)

        var databaseHelper = DatabaseHandler(this)
        var user : User? = databaseHelper.getUser(username.toString())

        // Displaying tutor information
        name.setText(tutorObj!!.fullName)
        location.setText(tutorObj!!.location)
        askingPrice.setText(tutorObj!!.rate.toString())
        email.setText(tutorObj!!.userID)
        phoneNumber.setText(tutorObj!!.contactNumber)
        specialization1.setText(tutorObj!!.specialization1)
        specialization2.setText(tutorObj!!.specialization2)
        specialization3.setText(tutorObj!!.specialization3)

        // Setting up a click listener to apply to the tutor and go back to the student menu
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