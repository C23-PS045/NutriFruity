package com.linggash.nutrifruity.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.databinding.ActivityCameraBinding
import com.linggash.nutrifruity.ml.ConvertedModel
import com.linggash.nutrifruity.util.createFile
import com.linggash.nutrifruity.util.uriToFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer

class CameraActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var getFile: File? = null

    private val mInputSize = 100
    private val mModelPath = "converted_model.tflite"
    private val mLabelPath = "label.txt"

    private lateinit var binding: ActivityCameraBinding

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@CameraActivity)
                getFile = myFile
                classifyImage()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.captureImage.setOnClickListener { takePhoto() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun classifyImage(){
        val bitmap = BitmapFactory.decodeFile(getFile?.path)
//        val classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
//
//        val result = classifier.recognizeImage(bitmap)
//        runOnUiThread{
//            Toast.makeText(this, result[0].title, Toast.LENGTH_SHORT).show()
//        }
        val dimension  = Math.min(bitmap.width, bitmap.height)
        var image = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension)
        image = Bitmap.createScaledBitmap(image, mInputSize, mInputSize, false)

        val model = ConvertedModel.newInstance(this)
        val byteBuffer = ByteBuffer.allocateDirect(4 * mInputSize * mInputSize * 3)
        val intValues = IntArray(mInputSize * mInputSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0

        for (i in 1..mInputSize){
            for (j in 1.. mInputSize){
                val values = intValues[pixel++]
                byteBuffer.putFloat((values shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((values shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((values and 0xFF) * (1f / 1))
            }
        }

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("stroberi", "pisang", "jeruk", "alpukat", "apel", "jambu biji", "pepaya", "melon", "anggur", "semangkat", "mangga", "pir" )
        Toast.makeText(this, classes[maxPos], Toast.LENGTH_SHORT).show()

        // Releases model resources if no longer used.
        model.close()
    }

    private fun setView() {
        binding.captureImage.setContent {
            OutlinedButton(
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange_primary)),
                border = null,
                contentPadding = PaddingValues(20.dp),
                onClick = {
                    takePhoto()
                }
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = getString(R.string.take_picture),
                )
            }
        }
        binding.btnGallery.setContent {
            OutlinedButton(
                shape = CircleShape,
                border = BorderStroke(5.dp, Color.White),
                contentPadding = PaddingValues(10.dp),
                onClick = {
                    startGallery()
                }
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(R.drawable.ic_gallery),
                    contentDescription = getString(R.string.gallery),
                )
            }
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra("picture", photoFile)
                    intent.putExtra(
                        "isBackCamera",
                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    getFile = photoFile
                    classifyImage()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}