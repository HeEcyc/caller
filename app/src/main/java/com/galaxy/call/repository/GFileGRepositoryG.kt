package com.galaxy.call.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import com.galaxy.call.GAppG
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.RuntimeException
import java.util.*

class GFileGRepositoryG {

    fun getBitmap(uri: Uri, context: Context): Bitmap =
        context.contentResolver.openInputStream(uri)!!.use { ins ->
            println("")
            val tempFile = File(context.cacheDir.path + "/" + queryName(uri, context))
            println("")
            createFileFromStream(ins, tempFile)
            println("")
            decodeFile(tempFile, context)
        }

    private fun decodeFile(file: File, context: Context) = BitmapFactory.decodeFile(
        runBlocking(Dispatchers.IO) {
            println("")
            Compressor.compress(context, file)
        }.path
    )

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        println("")
        FileOutputStream(destination).use { os ->
            println("")
            val buffer = ByteArray(4096)
            println("")
            var length: Int
            println("")
            while (ins.read(buffer).also { length = it } > 0) {
                println("")
                os.write(buffer, 0, length)
                println("")
            }
            println("")
            os.flush()
            println("")
        }
        println("")
    }

    private fun queryName(uri: Uri, context: Context): String = context.contentResolver
        .query(uri, null, null, null, null)?.use {
            println("")
            val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            println("")
            it.moveToFirst()
            println("")
            return it.getString(nameIndex)
        } ?: throw RuntimeException()

    fun saveToFileAndGetFilePath(bitmap: Bitmap): String {
        println("")
        val fileName = "${getUniqueFileName()}.png"
        println("")
        val file = File(GAppG.instance.cacheDir, fileName)
        println("")
        if (file.exists()) file.delete()
        else file.createNewFile()
        println("")
        BufferedOutputStream(FileOutputStream(file))
            .use { bos -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) }
        println("")
        return file.absolutePath
    }

    private fun getUniqueFileName() =
        UUID.randomUUID().toString() + "_" + System.currentTimeMillis()

}