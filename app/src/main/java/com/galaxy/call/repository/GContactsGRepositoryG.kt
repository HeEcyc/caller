package com.galaxy.call.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.galaxy.call.model.contact.UserContact
import java.util.regex.Pattern

class GContactsGRepositoryG(private val context: Context) {

    val contacts by lazy { readContacts() }

    private val projection = arrayOf(
        ContactsContract.PhoneLookup._ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.PhoneLookup.HAS_PHONE_NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
    )

    fun getContact(phoneNumber: String?): UserContact? {
        println("")
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        println("")
        context.contentResolver.query(uri, projection, null, null, null)
            ?.use { cursor ->
                println("")
                val cursorIndexes = getCursorIndexes(cursor)
                println("")
                if (cursor.moveToNext()) return readContactFromCursor(cursor, cursorIndexes)
                    .also { it.contactNumber = phoneNumber }
                println("")
            }
        println("")
        return null
    }

    private fun readContactFromCursor(cursor: Cursor, cursorIndexes: CursorIndexes) = UserContact(
        cursor.getLong(cursorIndexes.idIndex),
        cursor.getString(cursorIndexes.nameIndex),
        photoThumbnailUri = cursor.getString(
            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
        )
    )

    private fun getCursorIndexes(cursor: Cursor) = CursorIndexes(
        cursor.getColumnIndex(ContactsContract.Contacts._ID),
        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
    )

    private fun readContacts(): List<UserContact> {
        println("")
        val loadedContacts = mutableListOf<UserContact>()
        println("")
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            println("")
            val cursorIndexes = getCursorIndexes(cursor)
            println("")
            val hasNumberIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            println("")
            while (cursor.moveToNext()) {
                println("")
                val contact = readContactFromCursor(cursor, cursorIndexes)
                println("")
                if (cursor.getInt(hasNumberIndex) > 0) {
                    println("")
                    readPhoneNumber(contact)
                    println("")
                    loadedContacts.add(contact)
                    println("")
                }
                println("")
            }
            println("")
        }
        println("")
        return loadedContacts.sortedBy { it.contactName }.sortedWith { o1, o2 -> when {
            o1.contactName.startsWithNotLetter() && !o2.contactName.startsWithNotLetter() -> 1
            !o1.contactName.startsWithNotLetter() && o2.contactName.startsWithNotLetter() -> -1
            else -> 0
        } }
    }

    private fun String?.startsWithNotLetter() = Pattern
        .compile("^[0-9[^\\w]].*")
        .matcher(this ?: "")
        .matches()

    private fun readPhoneNumber(contact: UserContact) {
        println("")
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contact.contactId.toString()),
            null
        )?.use { numberCursor ->
            println("")
            val phoneNumbers = mutableListOf<String>()
            println("")
            while (numberCursor.moveToNext()) {
                println("")
                val numberIndex = numberCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                println("")
                phoneNumbers.add(numberCursor.getString(numberIndex))
                println("")
            }
            println("")
            contact.contactNumber = phoneNumbers.first()
            println("")
            contact.phoneNumbers = phoneNumbers.distinct()
            println("")
        }
    }

    private data class CursorIndexes(val idIndex: Int, val nameIndex: Int)

}