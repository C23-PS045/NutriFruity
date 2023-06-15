package com.linggash.nutrifruity.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.SettingPreferences
import com.linggash.nutrifruity.data.dataStore
import com.linggash.nutrifruity.databinding.ActivityCameraBinding
import com.linggash.nutrifruity.ui.result.CameraResultActivity
import com.linggash.nutrifruity.util.createFile
import com.linggash.nutrifruity.util.rotateFile
import com.linggash.nutrifruity.util.uriToFile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CameraActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var binding: ActivityCameraBinding

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var soundIdCommon: Int = 0
    private var spLoaded = false

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@CameraActivity)
                val intent = Intent(this@CameraActivity, CameraResultActivity::class.java)
                intent.putExtra(PICTURE, myFile)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pref : Boolean
        runBlocking {
            pref = SettingPreferences.getInstance(dataStore).getSoundSetting().first()
        }

        setView(pref)
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

    private fun setView(isOn : Boolean) {
        if (isOn){
            sp = SoundPool.Builder()
                .setMaxStreams(10)
                .build()
            sp.setOnLoadCompleteListener{ _, _, status ->
                if (status == 0){
                    spLoaded = true
                }else {
                    Toast.makeText(this, "Gagal load", Toast.LENGTH_SHORT).show()
                }
            }
            soundId = sp.load(this, R.raw.camera, 1)
            soundIdCommon = sp.load(this, R.raw.btn, 1)
        }
        binding.captureImage.setOnClickListener {
            takePhoto()
        }
        binding.btnGallery.setOnClickListener {
            if (spLoaded) {
                sp.play(soundIdCommon, 1f, 1f, 0, 0, 1f)
            }
            startGallery()
        }
    }

    private fun takePhoto() {
        if (spLoaded) {
            sp.play(soundId, 1f, 1f, 0, 0, 1f)
        }
        binding.progressBar.visibility = View.VISIBLE
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    rotateFile(photoFile, true)
                    val intent = Intent(this@CameraActivity, CameraResultActivity::class.java)
                    intent.putExtra(PICTURE, photoFile)
                    startActivity(intent)
                    finish()
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
        const val PICTURE = "picture"
    }
}