package com.linggash.nutrifruity.ui.screen.camera

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.common.UiState
import com.linggash.nutrifruity.util.rotateFile
import com.linggash.nutrifruity.util.timeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val application: Application,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<File>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<File>>
        get() = _uiState

    private var imageCapture: ImageCapture? = null
    private val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    fun getFile(file: File){
        _uiState.value = UiState.Success(file)
    }

    private fun createFile(): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.jpg")
    }

    fun takePhoto(
        context: Context,
    ) {
        _uiState.value = UiState.Loading
        val imageCapture = imageCapture ?: return
        val photoFile = createFile()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        context,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    rotateFile(photoFile, true)
                    _uiState.value = UiState.Success(photoFile)
                }
            }
        )
    }

     fun startCamera(
        lifeCycleOwner: LifecycleOwner,
        context: Context,
        previewView: PreviewView,
        failedString: String
    ) {
         val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifeCycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    context,
                    failedString,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun startGallery(
        launcherIntentGallery: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

}