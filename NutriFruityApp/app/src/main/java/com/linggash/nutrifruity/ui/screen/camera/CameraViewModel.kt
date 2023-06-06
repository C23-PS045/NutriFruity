package com.linggash.nutrifruity.ui.screen.camera

import android.app.Application
import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.util.timeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    fun createFile(): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.jpg")
    }
}