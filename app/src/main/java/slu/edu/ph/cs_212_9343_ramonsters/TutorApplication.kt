package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream
import java.io.InputStream


/**
* @author Tank, Rithik
* This class represents the backend functionality of the process of being a tutor.
* It handles the process of a user applying to be a tutor.
*/
class TutorApplication : AppCompatActivity() {

    lateinit var imageButton : Button
    private var resume : ByteArray? = null
    lateinit var resumeUploaded : TextView
    private var username: String? = null
    private var user : User? = null

    lateinit var name : TextView
    lateinit var locationField : EditText
    lateinit var specializationField1 : EditText
    lateinit var specializationField2 : EditText
    lateinit var specializationField3 : EditText
    lateinit var rateField : EditText
    lateinit var applyButton : Button
    lateinit var pfp : ImageView
    lateinit var backButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_application)

        val intent = getIntent();
        val username = intent.getStringExtra("user")
        var databaseHelper = DatabaseHandler(this)
        val user = databaseHelper.getUser(username.toString())
        name = findViewById(R.id.name)
        name.setText(user!!.fullName)

        imageButton = findViewById(R.id.uploadImageButton)
        resumeUploaded = findViewById(R.id.resumeUpload)
        locationField = findViewById(R.id.locationField)
        specializationField1 = findViewById(R.id.specialization1)
        specializationField2 = findViewById(R.id.specialization2)
        specializationField3 = findViewById(R.id.specialization3)
        rateField = findViewById(R.id.askingRateField)
        applyButton = findViewById(R.id.applyButton)
        pfp = findViewById(R.id.pfp)
        backButton = findViewById(R.id.backButton)

        var bitmap = BitmapFactory.decodeByteArray(user.PFP,0,user.PFP!!.size)
        pfp.setImageBitmap(bitmap)


        backButton.setOnClickListener() {
            var newIntent = Intent(this,ProfileMenu::class.java)
            newIntent.putExtra("user", user!!.userID)
            startActivity(newIntent)
        }

        imageButton.setOnClickListener {
            openFileChooser()
        }

        applyButton.setOnClickListener {
            if (resume != null) {

                databaseHelper.applyToBecomeTutor(
                    username!!,
                    locationField.text.toString(),
                    specializationField1.text.toString(),
                    specializationField2.text.toString(),
                    specializationField3.text.toString(),
                    rateField.text.toString().toDouble(),
                    resume
                )
                val toast = Toast.makeText(this, "Application sent.", Toast.LENGTH_LONG)
                toast.show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            } else {
                val toast = Toast.makeText(this, "Upload a resume.", Toast.LENGTH_LONG)
                toast.show()
            }
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
                    resume = imageData
                }

                if (resume != null) {
                    val toast = Toast.makeText(this, "Resume Upload Successful", Toast.LENGTH_LONG)
                    toast.show()

                } else {
                    Log.e("imagePickerLauncher", "resume is null")
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