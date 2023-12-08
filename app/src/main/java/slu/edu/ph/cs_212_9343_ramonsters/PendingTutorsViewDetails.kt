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

class PendingTutorsViewDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_tutors_view_details)

        var databaseHandler : DatabaseHandler = DatabaseHandler(this)

        var name : TextView = findViewById(R.id.name)
        var location : TextView = findViewById(R.id.location)
        var askingPrice : TextView = findViewById(R.id.askingPriceDisplay)
        var email : TextView = findViewById(R.id.email)
        var pfp : ImageView = findViewById(R.id.tutorPicture)
        var phoneNumber : TextView = findViewById(R.id.phoneNumber)
        var specialization1 : TextView = findViewById(R.id.specialization1)
        var specialization2 : TextView = findViewById(R.id.specialization2)
        var specialization3 : TextView = findViewById(R.id.specialization3)


        var backButton : ImageButton = findViewById(R.id.backButton)
        var resumeText : TextView = findViewById(R.id.resumeText)
        var resumeImg : ImageView = findViewById(R.id.resumeImg)
        var backgroundGray : View = findViewById(R.id.backgroundGray)
        var backButton2 : ImageButton = findViewById(R.id.backButton2)
        var viewResume : Button = findViewById(R.id.viewResume)
        var acceptButton : Button = findViewById(R.id.acceptButton)
        var rejectButton : Button = findViewById(R.id.rejectButton)
        var tutorAcceptedTxt : TextView = findViewById(R.id.tutorAcceptedTxt)

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

        var bitmap = BitmapFactory.decodeByteArray(tutorObj.PFP,0,tutorObj.PFP!!.size)
        pfp.setImageBitmap(bitmap)


        var firstName : String = tutorObj.fullName.toString().split(" ")[0]
        resumeText.setText("$firstName-resume.jpg")

        var bitmap2 = BitmapFactory.decodeByteArray(tutorObj.resume,0,tutorObj.resume!!.size)
        resumeImg.setImageBitmap(bitmap2)

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
            var newIntent = Intent(this,AdminMenu::class.java)
            newIntent.putExtra("user", username)
            startActivity(newIntent)
        }


        acceptButton.setOnClickListener() {
            databaseHandler.acceptOrRejectTutor(tutorObj.userID,1)
            tutorAcceptedTxt.visibility = View.VISIBLE
            tutorAcceptedTxt.text = "TUTOR ACCEPTED"
        }
        rejectButton.setOnClickListener() {
            databaseHandler.acceptOrRejectTutor(tutorObj.userID,0)
            tutorAcceptedTxt.visibility = View.VISIBLE
            tutorAcceptedTxt.text = "TUTOR REJECTED"
        }



    }
}