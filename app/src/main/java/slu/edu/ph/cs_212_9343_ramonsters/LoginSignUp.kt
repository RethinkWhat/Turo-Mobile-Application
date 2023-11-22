package slu.edu.ph.cs_212_9343_ramonsters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginSignUp : AppCompatActivity() {

    lateinit var logInButton: Button
    lateinit var signUpButton : Button
    lateinit var emailField: EditText
    lateinit var passwordField : EditText
    lateinit var email : String
    lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginsignup)

        logInButton = findViewById(R.id.login)
        signUpButton = findViewById(R.id.SignUpButton)
        passwordField = findViewById(R.id.EnterPasswordField)
        emailField = findViewById(R.id.EmailAddressField)


        logInButton.setOnClickListener {
            email = emailField.getText().toString()
            password = passwordField.getText().toString()

            if (email == "tutor@email.com" && password == "tutor123") {
                setContentView(R.layout.tutormenu)
            } else if (email == "student@email.com" && password == "student123")
                setContentView(R.layout.studentmenu)
        }

        signUpButton.setOnClickListener {
            email = emailField.text.toString()
            password = passwordField.text.toString()

        }




    }
}