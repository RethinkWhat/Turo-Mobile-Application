/**
 * This class represents the main activity for the student menu in the tutoring application.
 * It displays a list of approved tutors, pending tutor applications, and a list of potential tutors.
 * Students can view details of tutors and navigate to their profiles.
 *
 */

package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentMenu : AppCompatActivity() {

    lateinit var helloMessage: TextView
    lateinit var viewDetails: Button

    /**
     * Overrides the onCreate method to initialize the activity.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_menu)

        var profileRedirectButton: ImageButton = findViewById(R.id.profileRedirectButton)

        /**
         * When needing information from the database this DatabaseHandler obj is what you will use.
         * Pertinent data for this menu is the list of pending tutors
         */
        // Database handling for retrieving information about tutors
        var databaseHelper = DatabaseHandler(this)

        var intent = getIntent()
        val username = intent.getStringExtra("user")

        // UI elements initialization
        val approvedTutorsMsg : TextView = findViewById(R.id.approvedTutorsMsg)
        val pendingApplicationsMsg : TextView = findViewById(R.id.pendingApplicationsMsg)
        val pendingApplications : TextView = findViewById(R.id.pendingApplications)
        val approvedTutor : TextView = findViewById(R.id.approvedTutor)
        val approvedTutorsRecyclerView: RecyclerView = findViewById(R.id.approvedTutors_recycler_view)
        val pendingApplicationRecyclerView: RecyclerView = findViewById(R.id.pendingApplications_recycler_view)

        // Clearing initial messages and background colors
        approvedTutorsMsg.text = ""
        pendingApplicationsMsg.text = ""
        approvedTutorsRecyclerView.setBackgroundColor(255)
        pendingApplicationRecyclerView.setBackgroundColor(255)

        // Displaying a personalized welcome message to the user
        helloMessage = findViewById(R.id.helloMsg)
        var user: User? = databaseHelper.getUser(username.toString())
        helloMessage.setText("Hello ${user!!.fullName}!")

        // This variable already holds all of the pending tutors in the database
        Log.i("onCreate", "possibleTutors Create")
        var possibleTutors: ArrayList<User> = databaseHelper.getUsers(1)

        // Retrieving and displaying approved tutors if any
        if (databaseHelper.getUser(username!!)!!.confirmations != null) {
            var confirmedTutors: ArrayList<User> = databaseHelper.getConfirmed(username)
            approvedTutorsRecyclerView.adapter = TutorAdapter(confirmedTutors, username!!)
            approvedTutorsMsg.text = "Approved Tutors"

            //TODO: SET background to background_grey
            // approvedTutorsRecyclerView.setBackgroundColor(255)
        }

        // Retrieving and displaying pending tutor applications if any
        if (databaseHelper.getUser(username!!)!!.pendings != null) {
            var pendingTutors: ArrayList<User> = databaseHelper.getPendings(username)
            pendingApplicationRecyclerView.adapter = TutorAdapter(pendingTutors, username!!)
            pendingApplicationsMsg.text = "Pending Applications"

            //TODO: SET background to background_grey
            // pendingApplicationRecyclerView.setBackgroundColor(255)
        }

        Log.i("onCreate", "recyclerView Create")
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        Log.i("onCreate", "recyclerView Adapater Create")
        recyclerView.adapter = TutorAdapter(possibleTutors, username!!)
        Log.i("onCreate", "recyclerView Adapater End")

        profileRedirectButton.setOnClickListener() {
            val intent = Intent(this, TutorApplication::class.java)
            intent.putExtra("User", username)
            startActivity(intent)
        }
    }

    /**
     * Adapter class for the RecyclerView to display a list of tutors.
     *
     * @property tutors List of tutors to be displayed.
     * @property username Username of the current user.
     */

    class TutorAdapter(val tutors: ArrayList<User>, val username: String) :
        RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        /**
         * ViewHolder class to hold the view for each tutor item in the RecyclerView.
         *
         * @property tutorView The view for a tutor item.
         */
        class TutorViewHolder(val tutorView: View) : RecyclerView.ViewHolder(tutorView) {
            private val tutorTextView: TextView = tutorView.findViewById(R.id.tutor)
            private val tutorImageView: ImageView = tutorView.findViewById(R.id.tutorImageView)
            private val viewDetails: Button = tutorView.findViewById(R.id.viewDetailsButton)

            /**
             * Binds the data of a tutor to the ViewHolder.
             *
             * @param user User object representing the tutor.
             * @param context Context of the application.
             * @param username Username of the current user.
             */
            fun bind(user: User, context: Context, username: String) {
                tutorTextView.text = user.fullName
                var bitmap = BitmapFactory.decodeByteArray(user.PFP, 0, user.PFP!!.size)
                tutorImageView.setImageBitmap(bitmap)

                // Setting up a click listener to view details of the tutor
                viewDetails.setOnClickListener() {
                    val intent = Intent(context, ViewDetails::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("tutor", user.userID)
                    context.startActivity(intent)
                }
            }
        }

        /**
         * Creates a new instance of the ViewHolder.
         *
         * @param parent The parent ViewGroup.
         * @param viewType The type of the view.
         * @return TutorViewHolder instance.
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.availabletutors, parent, false)
            return TutorViewHolder(view)
        }
        /**
         * Returns the total number of tutors in the list.
         *
         * @return The total number of tutors.
         */

        override fun getItemCount(): Int {
            return tutors.size
        }

        /**
         * Binds the data of a tutor to the ViewHolder at the specified position.
         *
         * @param holder The ViewHolder to bind the data to.
         * @param position The position of the tutor in the list.
         */
        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            holder.bind(tutors[position], holder.itemView.context, username)
        }
    }
}