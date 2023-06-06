package com.linggash.nutrifruity.ui.screen.camera

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.theme.OrangePrimary
import com.linggash.nutrifruity.ui.theme.SpacingLarge
import com.linggash.nutrifruity.util.uriToFile
import java.io.File

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = hiltViewModel()
){
    CameraContent(
        viewModel = viewModel,
        modifier = modifier,
    )
}

@Composable
fun CameraContent(
    viewModel: CameraViewModel,
    modifier: Modifier
) {
    var imageCapture: ImageCapture? = null
    val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    val previewView = PreviewView(context)
    val failedString = stringResource(R.string.failed_show_camera)
    var getFile: File? = null

    val launcherIntentGallery = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, context)
                getFile = myFile
            }
        }
    }

    startCamera(
        imgCapture = {imageCapture = it},
        cameraSelector = cameraSelector,
        context = context,
        lifeCycleOwner = lifeCycleOwner,
        previewView = previewView,
        failedString = failedString
    )

    Box {
        AndroidView(
            factory = {
                previewView.apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = {
                startCamera(
                    imgCapture = {imageCapture = it},
                    cameraSelector = cameraSelector,
                    context = context,
                    lifeCycleOwner = lifeCycleOwner,
                    previewView = previewView,
                    failedString = failedString
                )
            }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = modifier
                .fillMaxWidth()
                .padding(SpacingLarge)
                .align(Alignment.BottomCenter)

        ) {
            val size = 70.dp
            OutlinedButton(
                shape = CircleShape,
                border = BorderStroke(5.dp, Color.White),
                contentPadding = PaddingValues(10.dp),
                onClick = {
                    startGallery{
                        launcherIntentGallery.launch(it)
                    }
                },
                modifier = modifier
                    .size(size)
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(R.drawable.ic_gallery),
                    contentDescription = stringResource(R.string.gallery),
                )
            }
            OutlinedButton(
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                border = null,
                contentPadding = PaddingValues(20.dp),
                onClick = {
                    takePhoto(
                        imgCapture = imageCapture,
                        context = context,
                        cameraSelector = cameraSelector,
                        viewModel = viewModel
                    )
                },
                modifier = modifier
                    .size(size)
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = stringResource(R.string.take_picture),
                )
            }
            Spacer(modifier.size(size))
        }
    }
}

private fun takePhoto(
    imgCapture: ImageCapture?,
    context: Context,
    cameraSelector: CameraSelector,
    viewModel: CameraViewModel,
) {
    val imageCapture = imgCapture ?: return
    val photoFile = viewModel.createFile()
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
                val intent = Intent()
                intent.putExtra("picture", photoFile)
                intent.putExtra(
                    "isBackCamera",
                    cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                )
//                setResult(MainActivity.CAMERA_X_RESULT, intent)
            }
        }
    )
}

private fun startCamera(
    imgCapture: (ImageCapture) -> Unit,
    cameraSelector: CameraSelector,
    context: Context,
    lifeCycleOwner: LifecycleOwner,
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

        val imageCapture = ImageCapture.Builder().build()
        imgCapture(imageCapture)

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

private fun startGallery(
    launcherIntentGallery: (Intent) -> Unit
) {
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT
    intent.type = "image/*"
    val chooser = Intent.createChooser(intent, "Choose a Picture")
    launcherIntentGallery(chooser)
}
