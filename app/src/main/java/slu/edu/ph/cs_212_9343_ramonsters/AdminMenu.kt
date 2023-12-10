package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

/**
 * AdminMenu class represents the activity for the admin's main menu.
 * It displays a welcome message, the admin's profile picture, and a list of pending tutor requests.
 */
class AdminMenu : AppCompatActivity() {

    // UI elements
    private lateinit var recycler1: RecyclerView
    private lateinit var helloMessage: TextView

    /**
     * Called when the activity is first created.
     * Sets up the UI, retrieves data from the database, and populates the pending tutor list.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        // Initialize DatabaseHelper to interact with the database
        val databaseHelper = DatabaseHandler(this)

        // Log the start of getting users
        Log.i("Get Users", "Get Users started")
        // Get pending tutor requests from the database
        val pendingTutors: ArrayList<User> = databaseHelper.getUsers(2)
        // Log the completion of getting users
        Log.i("Get Users", "Get Users finished")

        // Get the username from the Intent
        val intent = getIntent()
        val username = intent.getStringExtra("user")
        // Retrieve admin user information from the database
        val user = databaseHelper.getUser(username!!)

        // Set up the welcome message with the admin's name
        helloMessage = findViewById(R.id.helloMsg)
        helloMessage.text = "Hello ${user!!.fullName}!"

        // Decode and display the admin's profile picture in a button
        var bitmap = BitmapFactory.decodeByteArray(user.PFP, 0, user.PFP!!.size)
        var scaledBitMap = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
        var profileRedirectButton: Button = findViewById(R.id.profileRedirectButton)
        var imageOfRedirectButton : ImageView = findViewById(R.id.profileRedirectImage)
        imageOfRedirectButton.setImageBitmap(scaledBitMap)
        profileRedirectButton.setOnClickListener() {
            // Redirect to the AdminProfileMenu activity
            val intent = Intent(this, AdminProfileMenu::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
        }

        // Set up the RecyclerView for displaying pending tutor requests
        recycler1 = findViewById(R.id.pendingTutor1_recycler_view)
        if (pendingTutors.size > 0) {
            Log.i("adapter", "Adapter reached")
            // Set the adapter to populate the RecyclerView with pending tutor requests
            recycler1.adapter = TutorAdapter(pendingTutors, user!!.userID, R.layout.pendingtutors)
        }
    }

    /**
     * TutorAdapter class is a RecyclerView adapter for displaying pending tutor requests.
     */
    class TutorAdapter(val tutors: ArrayList<User>, val username: String, val layoutID: Int) :
        RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        // Arrays to hold users for each box in the RecyclerView
        var box1: ArrayList<User> = ArrayList()
        var box2: ArrayList<User> = ArrayList()
        var box3: ArrayList<User> = ArrayList()
        var box4: ArrayList<User> = ArrayList()

        /**
         * TutorViewHolder represents a view holder for each item in the RecyclerView.
         */
        class TutorViewHolder(val tutorView: View) : RecyclerView.ViewHolder(tutorView) {

            // UI elements for each pending tutor item
            private val pendingButton1: Button = tutorView.findViewById(R.id.pendingButton1)
            private val pendingButton2: Button = tutorView.findViewById(R.id.pendingButton2)
            private val pendingButton3: Button = tutorView.findViewById(R.id.pendingButton3)
            private val pendingButton4: Button = tutorView.findViewById(R.id.pendingButton4)

            private val pendingTutorName1: TextView = tutorView.findViewById(R.id.pendingTutorName1)
            private val pendingTutorName2: TextView = tutorView.findViewById(R.id.pendingTutorName2)
            private val pendingTutorName3: TextView = tutorView.findViewById(R.id.pendingTutorName3)
            private val pendingTutorName4: TextView = tutorView.findViewById(R.id.pendingTutorName4)

            private val pendingTutorLocation1: TextView =
                tutorView.findViewById(R.id.pendingTutorLocation1)
            private val pendingTutorLocation2: TextView =
                tutorView.findViewById(R.id.pendingTutorLocation2)
            private val pendingTutorLocation3: TextView =
                tutorView.findViewById(R.id.pendingTutorLocation3)
            private val pendingTutorLocation4: TextView =
                tutorView.findViewById(R.id.pendingTutorLocation4)

            private val pendingTutorImage1: ImageView =
                tutorView.findViewById(R.id.pendingTutorImage1)
            private val pendingTutorImage2: ImageView =
                tutorView.findViewById(R.id.pendingTutorImage2)
            private val pendingTutorImage3: ImageView =
                tutorView.findViewById(R.id.pendingTutorImage3)
            private val pendingTutorImage4: ImageView =
                tutorView.findViewById(R.id.pendingTutorImage4)

            /**
             * Binds data to the ViewHolder.
             */
            fun bind(
                user1: User,
                user2: User,
                user3: User,
                user4: User,
                context: Context,
                username: String
            ) {
                Log.i("BINDING BEING", "BINDING")
                // First box
                if (user1.userID != "") {
                    pendingTutorName1.text = user1.fullName
                    pendingTutorLocation1.text = user1.location
                    var bitmap1 = BitmapFactory.decodeByteArray(user1.PFP, 0, user1.PFP!!.size)
                    pendingTutorImage1.setImageBitmap(bitmap1)
                }

                if (user2.userID != "") {
                    // Second box
                    pendingTutorName2.visibility = View.VISIBLE
                    pendingTutorLocation2.visibility = View.VISIBLE
                    pendingTutorImage2.visibility = View.VISIBLE
                    pendingButton2.visibility = View.VISIBLE

                    pendingTutorName2.text = user2.fullName
                    pendingTutorLocation2.text = user2.location
                    var bitmap2 = BitmapFactory.decodeByteArray(user2.PFP, 0, user2.PFP!!.size)
                    pendingTutorImage2.setImageBitmap(bitmap2)
                }

                if (user3.userID != "") {
                    // Third box
                    pendingTutorName3.visibility = View.VISIBLE
                    pendingTutorLocation3.visibility = View.VISIBLE
                    pendingTutorImage3.visibility = View.VISIBLE
                    pendingButton3.visibility = View.VISIBLE

                    pendingTutorName3.text = user3.fullName
                    pendingTutorLocation3.text = user3.location
                    var bitmap3 = BitmapFactory.decodeByteArray(user3.PFP, 0, user3.PFP!!.size)
                    pendingTutorImage3.setImageBitmap(bitmap3)
                }

                if (user4.userID != "") {
                    // Fourth box
                    pendingTutorName4.visibility = View.VISIBLE
                    pendingTutorLocation4.visibility = View.VISIBLE
                    pendingTutorImage4.visibility = View.VISIBLE
                    pendingButton4.visibility = View.VISIBLE

                    pendingTutorName4.text = user4.fullName
                    pendingTutorLocation4.text = user4.location
                    var bitmap4 = BitmapFactory.decodeByteArray(user1.PFP, 0, user4.PFP!!.size)
                    pendingTutorImage4.setImageBitmap(bitmap4)
                }

                // Set up click listeners for each "Pending" button to view details
                pendingButton1.setOnClickListener() {
                    var intent = Intent(context, PendingTutorsViewDetails::class.java)
                    intent.putExtra("tutor", user1.userID)
                    context.startActivity(intent)
                }
                pendingButton2.setOnClickListener() {
                    var intent = Intent(context, PendingTutorsViewDetails::class.java)
                    intent.putExtra("tutor", user2.userID)
                    context.startActivity(intent)
                }
                pendingButton3.setOnClickListener() {
                    var intent = Intent(context, PendingTutorsViewDetails::class.java)
                    intent.putExtra("tutor", user3.userID)
                    context.startActivity(intent)
                }
                pendingButton4.setOnClickListener() {
                    var intent = Intent(context, PendingTutorsViewDetails::class.java)
                    intent.putExtra("tutor", user4.userID)
                    context.startActivity(intent)
                }
            }
        }

        /**
         * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            updateBoxes(tutors)
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutID, parent, false)
            return TutorViewHolder(view)
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         */
        override fun getItemCount(): Int {
            return tutors.size
        }

        /**
         * Called by RecyclerView to display the data at the specified position.
         */
        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            Log.i("Bind", "Bind started")
            // Determine the position in each box
            val box1Position = if (box1.isNotEmpty()) position % box1.size else 0
            val box2Position = if (box2.isNotEmpty()) position % box2.size else 0
            val box3Position = if (box3.isNotEmpty()) position % box3.size else 0
            val box4Position = if (box4.isNotEmpty()) position % box4.size else 0

            var blankUser = User("", "", "", "", 0, "", "", "", "", 0.0, 0, null, null, "", "")
            Log.i("holder.bind", "Binding attempt")
            // Bind data to the ViewHolder
            holder.bind(
                box1.getOrNull(box1Position) ?: blankUser,
                box2.getOrNull(box2Position) ?: blankUser,
                box3.getOrNull(box3Position) ?: blankUser,
                box4.getOrNull(box4Position) ?: blankUser,
                holder.itemView.context,
                username
            )
            Log.i("Bind", "Bind completed")
        }

        /**
         * Update the box arrays with users.
         */
        fun updateBoxes(users: ArrayList<User>) {
            Log.i("updateBoxes", "updateBoxes started")

            val boxCount = 4
            val boxSizes = IntArray(boxCount) { users.size / boxCount }  // Initialize with even distribution
            val remainder = users.size % boxCount

            // Distribute the remainder among the first few boxes
            for (i in 0 until remainder) {
                boxSizes[i]++
            }

            val boxes = listOf(box1, box2, box3, box4)

            for (i in users.indices) {
                val user = users[i]
                val boxIndex = i % boxCount
                boxes[boxIndex].add(user)
            }

            Log.i("updateBoxes", "updateBoxes finished")
        }
    }
}
