package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_menu)

        /**
         * When needing information from the database this DatabaseHandler obj is what you will use.
         * Pertinent data for this menu is the list of pending tutors
         */
        var databaseHelper = DatabaseHandler(this)

        // This variable already holds all of the pending tutors in the database
        Log.i("onCreate", "possibleTutors Create")
        var possibleTutors : ArrayList<User> = databaseHelper.getUsers(1)

        Log.i("onCreate", "recyclerView Create")
        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        Log.i("onCreate", "recyclerView Adapater Create")
        recyclerView.adapter = TutorAdapter(possibleTutors)
        Log.i("onCreate", "recyclerView Adapater End")
    }

    class TutorAdapter(val possibleTutors : ArrayList<User>) :
        RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        class TutorViewHolder(val tutorView : View) : RecyclerView.ViewHolder(tutorView) {
            private val tutorTextView: TextView = tutorView.findViewById(R.id.tutor)
            fun bind(word: String) {
                tutorTextView.text = word
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_student_menu,parent,false)
            return TutorViewHolder(view)
        }

        override fun getItemCount(): Int {
            return possibleTutors.size
        }

        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            holder.bind(possibleTutors[position].toString())
        }


    }

}