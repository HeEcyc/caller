package com.glasserino.caller.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import com.glasserino.caller.GlAppGl
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.RuntimeException
import java.util.*

class GlFileGlRepositoryGL {

    fun getBitmap(uri: Uri, context: Context): Bitmap =
        context.contentResolver.openInputStream(uri)!!.use { ins ->
            listOf<Any>().isEmpty()
            val tempFile = File(context.cacheDir.path + "/" + queryName(uri, context))
            listOf<Any>().isEmpty()
            createFileFromStream(ins, tempFile)
            listOf<Any>().isEmpty()
            decodeFile(tempFile, context)
        }

    private fun decodeFile(file: File, context: Context) = BitmapFactory.decodeFile(
        runBlocking(Dispatchers.IO) {
            listOf<Any>().isEmpty()
            Compressor.compress(context, file)
        }.path
    )

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        listOf<Any>().isEmpty()
        FileOutputStream(destination).use { os ->
            listOf<Any>().isEmpty()
            val buffer = ByteArray(4096)
            listOf<Any>().isEmpty()
            var length: Int
            listOf<Any>().isEmpty()
            while (ins.read(buffer).also { length = it } > 0) {
                listOf<Any>().isEmpty()
                os.write(buffer, 0, length)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
            os.flush()
        }
        listOf<Any>().isEmpty()
    }

    private fun queryName(uri: Uri, context: Context): String = context.contentResolver
        .query(uri, null, null, null, null)?.use {
            listOf<Any>().isEmpty()
            val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            listOf<Any>().isEmpty()
            it.moveToFirst()
            listOf<Any>().isEmpty()
            return it.getString(nameIndex)
        } ?: throw RuntimeException()

    fun saveToFileAndGetFilePath(bitmap: Bitmap): String {
        listOf<Any>().isEmpty()
        val fileName = "${getUniqueFileName()}.png"
        listOf<Any>().isEmpty()
        val file = File(GlAppGl.instance.cacheDir, fileName)
        listOf<Any>().isEmpty()
        if (file.exists()) file.delete()
        else file.createNewFile()
        listOf<Any>().isEmpty()
        BufferedOutputStream(FileOutputStream(file))
            .use { bos -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) }
        listOf<Any>().isEmpty()
        return file.absolutePath
    }

    private fun getUniqueFileName() =
        UUID.randomUUID().toString() + "_" + System.currentTimeMillis()

}