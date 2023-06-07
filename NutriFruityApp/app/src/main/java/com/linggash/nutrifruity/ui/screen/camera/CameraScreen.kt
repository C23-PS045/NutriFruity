package com.linggash.nutrifruity.ui.screen.camera

import android.app.Activity.RESULT_OK
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.common.UiState
import com.linggash.nutrifruity.ui.theme.OrangePrimary
import com.linggash.nutrifruity.ui.theme.SpacingLarge
import com.linggash.nutrifruity.ui.theme.SpacingStandard
import com.linggash.nutrifruity.util.uriToFile
import java.io.File

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = hiltViewModel(),
){
    CameraContent(
        viewModel = viewModel,
        modifier = modifier,
    )
}

@Composable
fun CameraContent(
    viewModel: CameraViewModel,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    val previewView = PreviewView(context)
    val failedString = stringResource(R.string.failed_show_camera)

    val launcherIntentGallery = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, context)
                viewModel.getFile(myFile)
            }
        }
    }

    viewModel.startCamera(
        context = context,
        lifeCycleOwner = lifeCycleOwner,
        previewView = previewView,
        failedString = failedString,
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
                viewModel.startCamera(
                    context = context,
                    lifeCycleOwner = lifeCycleOwner,
                    previewView = previewView,
                    failedString = failedString,
                )
            }
        )
        viewModel.uiState.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Error -> {

                }
                UiState.Loading -> {
                }
                is UiState.Success -> {
                    ImageDialog(
                        modifier = modifier,
                        file = uiState.data,
                        onDismiss = {
                            viewModel.closeDialog()
                            viewModel.startCamera(
                                context = context,
                                lifeCycleOwner = lifeCycleOwner,
                                previewView = previewView,
                                failedString = failedString,
                            )
                        }
                    )
                }
            }
        }
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
                    viewModel.startGallery(launcherIntentGallery)
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
                    viewModel.takePhoto(context = context)
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDialog(
    modifier: Modifier,
    file: File,
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(SpacingStandard),
            modifier = modifier
                .fillMaxWidth(0.95f)
                .border(1.dp, color = OrangePrimary, shape = RoundedCornerShape(SpacingStandard))
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(SpacingStandard)
            ) {
                AsyncImage(
                    model = BitmapFactory.decodeFile(file.path),
                    contentDescription = "Buah"
                )
                Text(text = "Ini adalah buah ....")
                Row(modifier = modifier.fillMaxWidth()) {
                    Button(
                        onClick = {onDismiss()}
                    ) {
                        Text(text = "Kembali")
                    }
                }
            }
        }
    }
}