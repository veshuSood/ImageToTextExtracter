package com.example.textextracter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {
    lateinit var result: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val camera=findViewById<ImageView>(R.id.btnCam)
        val erase=findViewById<ImageView>(R.id.btmEdit)
        val copy=findViewById<ImageView>(R.id.btnCopy)

        result=findViewById(R.id.resultTV)
      //  on clicking camera td do anything setOnClickListener is used
        camera.setOnClickListener {
            //open up cam and store image
            //on clicked image we will run ML algo to extract text
            val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(intent.resolveActivity(packageManager)!=null){
                //want to receive image and start for extraction i.e deliver to ML model
                startActivityForResult(intent,123)//other things could also be used
                //request code is used to check if picture clicked or not
            }
            else{
                //something went wrong
                Toast.makeText(this,"Oops something is Fishy 🐟🌊!!",Toast.LENGTH_SHORT).show()
            }

        }
        //on clicking edit
        erase.setOnClickListener{
            result.setText("")
        }
        //on clicking copy
        copy.setOnClickListener{
            val clipboard=getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip=ClipData.newPlainText("Copied Text",result.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this,"Text copied to clipboard",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123 && resultCode== RESULT_OK){
        // this means camera app worked correctly
        // now we send data to ML algo for text extraction
        val extras=data?.extras //null for developer safety
        val bitmap=extras?.get("data") as Bitmap
        //type casting(as ML model only accepts data in buffer or bitmap)
            detecTextUsingML(bitmap)//function to detect
        }
    }

    private fun detecTextUsingML(bitmap: Bitmap?) {
        //  using Latin script library
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap!!, 0)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ...
                result.setText(visionText.text.toString())
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Toast.makeText(this,"Oops something is Fishy 🐟🌊!!",Toast.LENGTH_SHORT).show()

            }
        //  using Devanagari script library
        val recognizerHindi = TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())
    }


}