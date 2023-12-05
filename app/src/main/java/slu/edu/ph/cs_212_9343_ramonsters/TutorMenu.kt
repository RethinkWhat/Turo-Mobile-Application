package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView

class TutorMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_menu)


        /**
         * When needing information from the database this DatabaseHandler obj is what you will use.
         * Pertinent data for this menu is the list of pending tutors
         */
        var databaseHelper = DatabaseHandler(this)


        val studentApplicationRecyclerView: RecyclerView = findViewById(R.id.studentApplication_recycler_view)
        val studentApplicationRecyclerView2: RecyclerView = findViewById(R.id.studentApplication_recycler_view2)
        val studentApplicationRecyclerView3: RecyclerView = findViewById(R.id.studentApplication_recycler_view3)
        var users: ArrayList<User> = databaseHelper.getUsers(2) //TODO:Change method when admin page finished
        var size = users.size / 3

        var box1 : ArrayList<User> = ArrayList()
        var box2 : ArrayList<User> = ArrayList()
        var box3 : ArrayList<User> = ArrayList()

        for (x in 0 until size) {
            box1.add(users.get(x))
        }
        for (x in size until(size+size)) {
            box2.add(users.get(x))
        }
        for (x in (size+size)until (size*3)) {
            box3.add(users.get(x))
        }
        if (size % 3 ==2) {
            box1.add(users.get(users.size-1))
            box2.add(users.get(users.size-2))
        }
        if (size % 3 ==1) {
            box1.add(users.get(users.size-1))
        }

        studentApplicationRecyclerView.adapter =TutorAdapter(box1, "ramonjasmin@gmail.com"!!)
        studentApplicationRecyclerView2.adapter =TutorAdapter(box2, "ramonjasmin@gmail.com"!!)
        studentApplicationRecyclerView3.adapter =TutorAdapter(box3, "ramonjasmin@gmail.com"!!)
    }


    class TutorAdapter(val tutors: ArrayList<User>, val username: String) :
        RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        class TutorViewHolder(val tutorView: View) : RecyclerView.ViewHolder(tutorView) {
            private val tutorTextView: TextView = tutorView.findViewById(R.id.tutor)
            private val tutorImageView: ImageView = tutorView.findViewById(R.id.tutorImageView)
            private val rejectButton: Button = tutorView.findViewById(R.id.rejectButton)
            private val acceptButton: Button = tutorView.findViewById(R.id.acceptButton)
            private val acceptedText : TextView = tutorView.findViewById(R.id.studentAcceptedTxt)

            fun bind(user: User, context: Context, username: String) {
                tutorTextView.text = user.fullName
                var bitmap = BitmapFactory.decodeByteArray(user.PFP, 0, user.PFP!!.size)
                tutorImageView.setImageBitmap(bitmap)


                acceptButton.setOnClickListener() {
                    acceptedText.visibility = View.VISIBLE
                    acceptButton.visibility = View.GONE
                    rejectButton.visibility = View.GONE

                }
                rejectButton.setOnClickListener() {
                    acceptedText.visibility = View.VISIBLE
                    acceptedText.setText("STUDENT REJECTED")
                    acceptButton.visibility = View.GONE
                    rejectButton.visibility = View.GONE
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.betutoredapplication, parent, false)
            return TutorViewHolder(view)
        }

        override fun getItemCount(): Int {
            return tutors.size
        }

        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            holder.bind(tutors[position], holder.itemView.context, username)
        }
    }
}