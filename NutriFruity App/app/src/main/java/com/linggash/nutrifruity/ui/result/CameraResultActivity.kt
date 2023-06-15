package com.linggash.nutrifruity.ui.result

import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.databinding.ActivityCameraResultBinding
import com.linggash.nutrifruity.tflite.Classifier
import com.linggash.nutrifruity.ui.camera.CameraActivity
import java.io.File

class CameraResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val picture = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(CameraActivity.PICTURE, File::class.java)
        } else {
            intent.getSerializableExtra(CameraActivity.PICTURE) as File
        }

        if (picture != null) {
            classifyImage(picture)
            binding.imgFruitCameraResult.setImageBitmap(BitmapFactory.decodeFile(picture.path))
        }else {
            Toast.makeText(this@CameraResultActivity, "Gambar tidak ada", Toast.LENGTH_SHORT).show()
        }

        binding.btnResultBack.setOnClickListener {
            finish()
        }
        binding.btnToDetail.setOnClickListener {

        }
    }

    private fun classifyImage(file: File){
        val bitmap = BitmapFactory.decodeFile(file.path)
        val classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        val result = classifier.recognizeImage(bitmap)
        val text = getString(R.string.this_is_fruit) + " " + result[0].title
        binding.tvFruit.text = text
    }

    companion object {
        private const val mInputSize = 100
        private const val mModelPath = "converted_model.tflite"
        private const val mLabelPath = "label.txt"
    }
}