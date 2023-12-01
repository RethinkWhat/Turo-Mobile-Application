package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream
import java.io.InputStream

class signUp : AppCompatActivity() {

    lateinit var emailField : EditText
    lateinit var passwordField : EditText
    lateinit var nameField : EditText
    lateinit var signUpButton : Button
    lateinit var uploadPFPButton : ImageButton
    private var databaseHelper = DatabaseHandler(this)
    private var pfp : ByteArray? = null
    lateinit var imageMsg : TextView
    lateinit var contactField : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailField = findViewById(R.id.emailAddressField)
        passwordField = findViewById(R.id.passwordField)
        signUpButton = findViewById(R.id.signUpButton)
        uploadPFPButton = findViewById(R.id.uploadPFP)
        imageMsg = findViewById(R.id.imageUpload)
        nameField = findViewById(R.id.nameField)
        contactField = findViewById(R.id.contactField)
        Log.i("signUp", " SignUp Page clicked")


        uploadPFPButton.setOnClickListener() {
            openFileChooser()
        }




        signUpButton.setOnClickListener() {
            Log.i("signUpButton", " SignUpButton clicked")
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val name = nameField.text.toString()
            val contact = contactField.text.toString()
            Log.i("signUpButton", " SignUpButton reached")
            val newUser = User(email, password, name,contact,0,"",0.0,0, pfp,null,null,null)
            databaseHelper.addUser(newUser)
            val intent = Intent(this, StudentMenu::class.java)
            intent.putExtra("User", newUser.userID)
            startActivity(intent)
        }
    }

    private fun openFileChooser() {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
            Log.i("OpenFileChooser", "openFileChooser started")
        } catch (e: Exception) {
            Log.e("OpenFileChooser", "Exception: ${e.message}")
            e.printStackTrace()
        }
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let { uri ->
                    val inputStream: InputStream? = contentResolver.openInputStream(uri)
                    val imageData: ByteArray? = readBytes(inputStream)
                    pfp = imageData
                }

                if (pfp != null) {
                    imageMsg.text = "Image Upload Complete"
                    /** TO DISPLAY THE IMAGE */
                    /*
                    val bitmap = BitmapFactory.decodeByteArray(pfp, 0, pfp!!.size)
                    Log.i("bitmap", "Decode complete")
                    pfpPic.setImageBitmap(bitmap)
                    Log.i("pfpPic", "Set pic complete")
                     */
                } else {
                    Log.e("imagePickerLauncher", "pfp is null")
                }
            }
            Log.i("imagePickerLauncher", "imagePickerLauncher reached")
        }

    private fun readBytes(inputStream: InputStream?): ByteArray? {
        if (inputStream == null) return null
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        try {
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            Log.i("readBytes", "readBytes success")
            return byteBuffer.toByteArray()
        } catch (e: Exception) {
            Log.i("readBytes", "readBytes fail")
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
                byteBuffer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
}