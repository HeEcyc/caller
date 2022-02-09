package com.megaaa.caaall.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import com.megaaa.caaall.App
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.RuntimeException
import java.util.*

class FileRepository {

    fun getBitmap(uri: Uri, context: Context): Bitmap =
        context.contentResolver.openInputStream(uri)!!.use { ins ->
            val tempFile = File(context.cacheDir.path + "/" + queryName(uri, context))
            createFileFromStream(ins, tempFile)
            decodeFile(tempFile, context)
        }

    private fun decodeFile(file: File, context: Context) = BitmapFactory.decodeFile(
        runBlocking(Dispatchers.IO) {
            Compressor.compress(context, file)
        }.path
    )

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    }

    private fun queryName(uri: Uri, context: Context): String = context.contentResolver
        .query(uri, null, null, null, null)?.use {
            val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            return it.getString(nameIndex)
        } ?: throw RuntimeException()

    fun saveToFileAndGetFilePath(bitmap: Bitmap): String {
        val fileName = "${getUniqueFileName()}.png"
        val file = File(App.instance.cacheDir, fileName)

        if (file.exists()) file.delete()
        else file.createNewFile()

        BufferedOutputStream(FileOutputStream(file))
            .use { bos -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) }

        return file.absolutePath
    }

    private fun getUniqueFileName() =
        UUID.randomUUID().toString() + "_" + System.currentTimeMillis()

}