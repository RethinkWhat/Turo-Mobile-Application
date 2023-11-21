package slu.edu.ph.cs_212_9343_ramonsters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var logInButton: Button
    lateinit var signUpButton : Button
    lateinit var emailField: EditText
    lateinit var passwordField : EditText
    lateinit var email : String
    lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logInButton = findViewById(R.id.login)
        signUpButton = findViewById(R.id.SignUpButton)
        emailField = findViewById(R.id.EmailAddressField)




        logInButton.setOnClickListener {
            //email = emailField.getText().toString()
            //password = passwordField.getText().toString()
            setContentView(R.layout.activity_main2)
        }

        signUpButton.setOnClickListener {
            email = emailField.getText().toString()
            password = passwordField.getText().toString()

        }




    }
}