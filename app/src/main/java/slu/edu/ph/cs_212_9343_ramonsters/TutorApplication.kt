package slu.edu.ph.cs_212_9343_ramonsters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts

class TutorApplication : AppCompatActivity() {

    lateinit var imageButton : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_application)

        imageButton = findViewById(R.id.uploadImageButton)

        imageButton.setOnClickListener {
            openFileChooser()
        }
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Handle the selected image here, e.g., upload it to the server
                val selectedImageUri = result.data?.data
                // Implement your file upload logic with the selected image URI
                // You might want to use a networking library or AsyncTask for this operation
                // Example: FileUploadTask(selectedImageUri).execute()
            }
        }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }
}