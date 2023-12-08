package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.BitmapFactory
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class ViewDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)

        var databaseHandler = DatabaseHandler(this)

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

        var backButton : ImageButton = findViewById(R.id.backButton)
        var resumeText : TextView = findViewById(R.id.resumeText)
        var resumeImg : ImageView = findViewById(R.id.resumeImg)
        var backgroundGray : View = findViewById(R.id.backgroundGray)
        var backButton2 : ImageButton = findViewById(R.id.backButton2)

        var bookButton : Button = findViewById(R.id.acceptButton)

        var intent = getIntent()
        var tutor = intent.getStringExtra("tutor")
        var username = intent.getStringExtra("user")
        var user = databaseHandler.getUser(username!!)
        var tutorObj : User? = databaseHandler.getUser(tutor!!)

        name.setText(tutorObj!!.fullName)
        location.setText(tutorObj!!.location)
        askingPrice.setText(tutorObj!!.rate.toString())
        email.setText(tutorObj!!.userID)
        phoneNumber.setText(tutorObj!!.contactNumber)
        specialization1.setText(tutorObj!!.specialization1)
        specialization2.setText(tutorObj!!.specialization2)
        specialization3.setText(tutorObj!!.specialization3)
        var firstName : String = tutorObj.fullName.toString().split(" ")[0]
        resumeText.setText("$firstName-resume.jpg")

        var bitmap = BitmapFactory.decodeByteArray(tutorObj.resume,0,tutorObj.resume!!.size)
        resumeImg.setImageBitmap(bitmap)

        var bitmap2 = BitmapFactory.decodeByteArray(tutorObj.PFP,0,tutorObj.PFP!!.size)
        tutorPicture.setImageBitmap(bitmap2)

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
            databaseHandler.applyToATutor(username!!,tutor)
        }


    }
}