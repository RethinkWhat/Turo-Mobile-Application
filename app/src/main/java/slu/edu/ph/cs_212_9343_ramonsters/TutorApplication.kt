package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.InputStream

class TutorApplication : AppCompatActivity() {

    var databaseHelper = DatabaseHandler(this)
    lateinit var imageButton : ImageButton
    private var resume : ByteArray? = null
    lateinit var resumeUploaded : TextView

    //val intent = getIntent();
    //val username = intent.getStringExtra("User")
    //val user = databaseHelper.getUser(username.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_application)

        imageButton = findViewById(R.id.uploadImageButton)
        resumeUploaded = findViewById(R.id.resumeUpload)



        imageButton.setOnClickListener {
            openFileChooser()
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
                    resumeUploaded.text = "Resume Upload Complete"

                    // TODO: INSERT UPDATE STATEMENT OF USER SUCH THAT RESUME IS NO LONGER NULL
                    // TODO: CHANGE USER STATUS OF USER

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