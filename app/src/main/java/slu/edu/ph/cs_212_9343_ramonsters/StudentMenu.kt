package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentMenu : AppCompatActivity() {

    lateinit var helloMessage : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_menu)

        var profileRedirectButton : ImageButton = findViewById(R.id.profileRedirectButton)

        /**
         * When needing information from the database this DatabaseHandler obj is what you will use.
         * Pertinent data for this menu is the list of pending tutors
         */
        var databaseHelper = DatabaseHandler(this)

        var intent = getIntent()
        var username = intent.getStringExtra("user")

        helloMessage = findViewById(R.id.helloMsg)
        var user : User? = databaseHelper.getUser(username.toString())
        helloMessage.setText("Hello ${user!!.fullName}!")

        // This variable already holds all of the pending tutors in the database
        Log.i("onCreate", "possibleTutors Create")
        var possibleTutors : ArrayList<User> = databaseHelper.getUsers(1)

        Log.i("onCreate", "recyclerView Create")
        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        Log.i("onCreate", "recyclerView Adapater Create")
        recyclerView.adapter = TutorAdapter(possibleTutors)
        Log.i("onCreate", "recyclerView Adapater End")

        profileRedirectButton.setOnClickListener() {
            val intent = Intent(this, TutorApplication::class.java)
            intent.putExtra("User", username)
            startActivity(intent)
        }
    }

    class TutorAdapter(val possibleTutors : ArrayList<User>) :
        RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        class TutorViewHolder(val tutorView : View) : RecyclerView.ViewHolder(tutorView) {
            private val tutorTextView: TextView = tutorView.findViewById(R.id.tutor)
            private val tutorImageView: ImageView = tutorView.findViewById(R.id.tutorImageView)
            fun bind(user: User) {
                tutorTextView.text = user.fullName
                var bitmap = BitmapFactory.decodeByteArray(user.PFP,0,user.PFP!!.size)
                tutorImageView.setImageBitmap(bitmap)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.availabletutors,parent,false)
            return TutorViewHolder(view)
        }

        override fun getItemCount(): Int {
            return possibleTutors.size
        }

        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            holder.bind(possibleTutors[position])
        }


    }

}