package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Context
import android.content.Intent
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

class TutorMenu : AppCompatActivity() {

    private lateinit var recycler1: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_menu)

        val databaseHelper = DatabaseHandler(this)

        val intent = getIntent();
        val username = intent.getStringExtra("user")
        Log.i("Tutor Menu", "getIntent")
        val user = databaseHelper.getUser(username!!)
        Log.i("Tutor Menu", "getUser")

        val pendingTutors: ArrayList<User>? = databaseHelper.getPendings(user!!.userID)
        Log.i("Tutor Menu", "getPendings")
        recycler1 = findViewById(R.id.studentApplication_recycler_view)
        if (pendingTutors!!.size > 0) {
            Log.i("adapter", "Adapter reached")
            recycler1.adapter = TutorAdapter(pendingTutors!!, user!!.userID, R.layout.betutoredapplication)
        }
    }

    class TutorAdapter(val tutors: ArrayList<User>, val username: String, val layoutID: Int) :

        RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        var box1: ArrayList<User> = ArrayList()
        var box2: ArrayList<User> = ArrayList()
        var box3: ArrayList<User> = ArrayList()

        class TutorViewHolder(val tutorView: View) : RecyclerView.ViewHolder(tutorView) {

            private val tutorName1: TextView = tutorView.findViewById(R.id.tutor)
            private val tutorName2: TextView = tutorView.findViewById(R.id.tutor2)
            private val tutorName3: TextView = tutorView.findViewById(R.id.tutor3)

            private val tutorLocation1: TextView = tutorView.findViewById(R.id.tutorLocation)
            private val tutorLocation2: TextView = tutorView.findViewById(R.id.tutorLocation2)
            private val tutorLocation3: TextView = tutorView.findViewById(R.id.tutorLocation3)

            private val tutorImage1: ImageView = tutorView.findViewById(R.id.tutorImageView)
            private val tutorImage2: ImageView = tutorView.findViewById(R.id.tutorImageView2)
            private val tutorImage3: ImageView = tutorView.findViewById(R.id.tutorImageView3)

            private val acceptButton1 : Button = tutorView.findViewById(R.id.acceptButton)
            private val rejectButton1 : Button = tutorView.findViewById(R.id.rejectButton)
            private val acceptButton2 : Button = tutorView.findViewById(R.id.acceptButton2)
            private val rejectButton2 : Button = tutorView.findViewById(R.id.rejectButton2)
            private val acceptButton3 : Button = tutorView.findViewById(R.id.acceptButton3)
            private val rejectButton3 : Button = tutorView.findViewById(R.id.rejectButton2)

            private val studentAcceptedTxt : TextView = tutorView.findViewById(R.id.studentAcceptedTxt)
            private val studentAcceptedTxt2 : TextView = tutorView.findViewById(R.id.studentAcceptedTxt2)
            private val studentAcceptedTxt3 : TextView = tutorView.findViewById(R.id.studentAcceptedTxt3)

            fun bind( user1: User, user2: User, user3: User, context: Context, username: String) {
                Log.i("Tutor Menu", "TutorViewHolder")
                Log.i("BINDING BEING", "BINDING")

                val databaseHandler = DatabaseHandler(context)

                // First box
                if (user1.userID != "") {
                    tutorName1.setText(user1.fullName)
                    tutorLocation1.setText(user1.location)
                    var bitmap1 = BitmapFactory.decodeByteArray(user1.PFP, 0,user1.PFP!!.size)
                    tutorImage1.setImageBitmap(bitmap1)
                }

                //Second box
                if (user2.userID != "") {
                    tutorName2.setText(user2.fullName)
                    tutorLocation2.setText(user2.location)
                    var bitmap2 = BitmapFactory.decodeByteArray(user2.PFP, 0,user2.PFP!!.size)
                    tutorImage2.setImageBitmap(bitmap2)
                }




                if (user3.userID != "") {
                    // Third box
                    tutorName3.setText(user3.fullName)
                    tutorLocation3.setText(user3.location)
                    var bitmap3 = BitmapFactory.decodeByteArray(user3.PFP, 0,user3.PFP!!.size)
                    tutorImage3.setImageBitmap(bitmap3)
                }

                acceptButton1.setOnClickListener() {
                    studentAcceptedTxt.visibility = View.VISIBLE
                    studentAcceptedTxt.setText("STUDENT ACCEPTED")
                    acceptButton1.visibility = View.GONE
                    rejectButton1.visibility = View.GONE
                    databaseHandler.tutorAcceptOrRejectStudent(user1.userID,username,1)
                }
                rejectButton1.setOnClickListener() {
                    studentAcceptedTxt.visibility = View.VISIBLE
                    studentAcceptedTxt.setText("STUDENT REJECTED")
                    acceptButton1.visibility = View.GONE
                    rejectButton1.visibility = View.GONE
                    databaseHandler.tutorAcceptOrRejectStudent(user1.userID,username,0)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            updateBoxes(tutors)
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutID, parent, false)
            return TutorViewHolder(view)
        }

        override fun getItemCount(): Int {
            return tutors.size
        }

        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            Log.i("Bind", "Bind started")
            val box1Position = if (box1.isNotEmpty()) position % box1.size else 0
            val box2Position = if (box2.isNotEmpty()) position % box2.size else 0
            val box3Position = if (box3.isNotEmpty()) position % box3.size else 0

            var blankUser = User("","","","",0,"","","","",0.0,0,null,null,"","")
            Log.i("holder.bind", "Binding attempt")
            holder.bind(
                box1.getOrNull(box1Position) ?: blankUser,
                box2.getOrNull(box2Position) ?: blankUser,
                box3.getOrNull(box3Position) ?: blankUser,
                holder.itemView.context,
                username
            )
            Log.i("Bind", "Bind completed")
        }

        fun updateBoxes(users: ArrayList<User>) {
            Log.i("updateBoxes", "updateBoxes started")

            val boxCount = 3
            val boxSizes = IntArray(boxCount) { users.size / boxCount }  // Initialize with even distribution
            val remainder = users.size % boxCount

            // Distribute the remainder among the first few boxes
            for (i in 0 until remainder) {
                boxSizes[i]++
            }

            val boxes = listOf(box1, box2, box3)

            for (i in users.indices) {
                val user = users[i]
                val boxIndex = i % boxCount
                boxes[boxIndex].add(user)
            }

            Log.i("updateBoxes", "updateBoxes finished")
        }
    }
}
