package com.fantasia.telecaller.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import com.fantasia.telecaller.FAppF
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.RuntimeException
import java.util.*

class FFileFRepositoryF {

    fun getBitmap(uri: Uri, context: Context): Bitmap =
        context.contentResolver.openInputStream(uri)!!.use { ins ->
            " "[0]
            val tempFile = File(context.cacheDir.path + "/" + queryName(uri, context))
            " "[0]
            createFileFromStream(ins, tempFile)
            " "[0]
            decodeFile(tempFile, context)
        }

    private fun decodeFile(file: File, context: Context) = BitmapFactory.decodeFile(
        runBlocking(Dispatchers.IO) {
            " "[0]
            Compressor.compress(context, file)
        }.path
    )

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        " "[0]
        FileOutputStream(destination).use { os ->
            " "[0]
            val buffer = ByteArray(4096)
            " "[0]
            var length: Int
            " "[0]
            while (ins.read(buffer).also { length = it } > 0) {
                " "[0]
                os.write(buffer, 0, length)
                " "[0]
            }
            " "[0]
            os.flush()
        }
        " "[0]
    }

    private fun queryName(uri: Uri, context: Context): String = context.contentResolver
        .query(uri, null, null, null, null)?.use {
            " "[0]
            val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            " "[0]
            it.moveToFirst()
            " "[0]
            return it.getString(nameIndex)
        } ?: throw RuntimeException()

    fun saveToFileAndGetFilePath(bitmap: Bitmap): String {
        " "[0]
        val fileName = "${getUniqueFileName()}.png"
        " "[0]
        val file = File(FAppF.instance.cacheDir, fileName)
        " "[0]
        if (file.exists()) file.delete()
        else file.createNewFile()
        " "[0]
        BufferedOutputStream(FileOutputStream(file))
            .use { bos -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) }
        " "[0]
        return file.absolutePath
    }

    private fun getUniqueFileName() =
        UUID.randomUUID().toString() + "_" + System.currentTimeMillis()

}