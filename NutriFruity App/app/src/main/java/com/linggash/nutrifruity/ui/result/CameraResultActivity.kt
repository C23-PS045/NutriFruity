package com.linggash.nutrifruity.ui.result

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.databinding.ActivityCameraResultBinding
import com.linggash.nutrifruity.tflite.Classifier
import com.linggash.nutrifruity.ui.ViewModelFactory
import com.linggash.nutrifruity.ui.camera.CameraActivity
import com.linggash.nutrifruity.ui.detail.FruitDetailActivity
import com.linggash.nutrifruity.ui.list.FruitListActivity
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
    }

    private fun classifyImage(file: File){
        val bitmap = BitmapFactory.decodeFile(file.path)
        val classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        val result = classifier.recognizeImage(bitmap)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: CameraResultViewModel by viewModels { factory }
        viewModel.getFruitDetail(result[0].title.toLong()).observe(this@CameraResultActivity){ resultFruit ->
            when (resultFruit){
                Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val text = getString(R.string.this_is_fruit) + " " + resultFruit.data.fruit.name
                    binding.tvFruit.text = text

                    binding.btnToDetail.setOnClickListener {
                        val intent = Intent(this, FruitDetailActivity::class.java)
                        intent.putExtra(FruitListActivity.ID, resultFruit.data.fruit)
                        startActivity(intent)
                        finish()
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val mInputSize = 100
        private const val mModelPath = "converted_model.tflite"
        private const val mLabelPath = "label.txt"
    }
}