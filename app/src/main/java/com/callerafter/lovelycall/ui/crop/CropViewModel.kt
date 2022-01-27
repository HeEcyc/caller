package com.callerafter.lovelycall.ui.crop

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.repository.FileRepository
import com.callerafter.lovelycall.repository.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CropViewModel(
    val fileRepository: FileRepository,
    private val themeRepository: ThemeRepository
) : BaseViewModel() {

    fun createThemeForBitmap(bitmap: Bitmap, onFinish: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            themeRepository.saveNewTheme(fileRepository.saveToFileAndGetFilePath(bitmap))
            launch(Dispatchers.Main) { onFinish() }
        }

}