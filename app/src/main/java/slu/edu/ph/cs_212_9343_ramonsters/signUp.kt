package slu.edu.ph.cs_212_9343_ramonsters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class signUp : AppCompatActivity() {

    lateinit var emailField : EditText
    lateinit var passwordField : EditText
    lateinit var signUpButton : Button
    private var databaseHelper = DatabaseHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailField = findViewById(R.id.emailAddressField)
        passwordField = findViewById(R.id.passwordField)
        signUpButton = findViewById(R.id.signUpButton)
        Log.i("signUp", " SignUp Page clicked")

        signUpButton.setOnClickListener() {
            Log.i("signUpButton", " SignUpButton clicked")
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            Log.i("signUpButton", " SignUpButton reached")
            val newUser = User(email, password, 0,"",0.0,0)
            databaseHelper.addUser(newUser)
            setContentView(R.layout.activity_student_menu)
        }
    }
}