package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdminMenu : AppCompatActivity() {

    private lateinit var pendingButtons: List<Button>
    private lateinit var pendingTutorImages: List<ImageView>
    private lateinit var pendingTutorNames: List<TextView>
    private lateinit var pendingTutorLocations: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        val databaseHelper = DatabaseHandler(this)
        val pendingTutors: ArrayList<User> = databaseHelper.getUsers(2)

        pendingButtons = listOf(
            findViewById(R.id.pendingButton1),
            findViewById(R.id.pendingButton2),
            findViewById(R.id.pendingButton3),
            findViewById(R.id.pendingButton4)
        )
        pendingTutorImages = listOf(
            findViewById(R.id.pendingTutorImage1),
            findViewById(R.id.pendingTutorImage2),
            findViewById(R.id.pendingTutorImage3),
            findViewById(R.id.pendingTutorImage4)
        )
        pendingTutorNames = listOf(
            findViewById(R.id.pendingTutorName1),
            findViewById(R.id.pendingTutorName2),
            findViewById(R.id.pendingTutorName3),
            findViewById(R.id.pendingTutorName4)
        )
        pendingTutorLocations = listOf(
            findViewById(R.id.pendingTutorLocation1),
            findViewById(R.id.pendingTutorLocation2),
            findViewById(R.id.pendingTutorLocation3),
            findViewById(R.id.pendingTutorLocation4)
        )

        pendingTutorImages = listOf(
            findViewById(R.id.pendingTutorImage1),
            findViewById(R.id.pendingTutorImage2),
            findViewById(R.id.pendingTutorImage3),
            findViewById(R.id.pendingTutorImage4)
        )

        var bitmap = BitmapFactory.decodeByteArray(pendingTutors[0].PFP, 0, pendingTutors[0].PFP!!.size)
        pendingTutorImages.get(0).setImageBitmap(bitmap)

        var bitmap1 = BitmapFactory.decodeByteArray(pendingTutors[1].PFP, 0, pendingTutors[1].PFP!!.size)
        pendingTutorImages.get(1).setImageBitmap(bitmap1)

        var bitmap2 = BitmapFactory.decodeByteArray(pendingTutors[2].PFP, 0, pendingTutors[2].PFP!!.size)
        pendingTutorImages.get(2).setImageBitmap(bitmap2)

        var bitmap3 = BitmapFactory.decodeByteArray(pendingTutors[3].PFP, 0, pendingTutors[3].PFP!!.size)
        pendingTutorImages.get(3).setImageBitmap(bitmap3)

        for ((index, tutor) in pendingTutors.withIndex()) {
            pendingButtons[index].text = "View Details"
            pendingButtons[index].setOnClickListener {

                // Redirect to ViewDetails
                val intent = Intent(this, ViewDetails::class.java)
                intent.putExtra("username", tutor.userID)
                startActivity(intent)
            }
            pendingTutorNames[index].text = tutor.fullName
            pendingTutorLocations[index].text = tutor.location

        }
    }
}
