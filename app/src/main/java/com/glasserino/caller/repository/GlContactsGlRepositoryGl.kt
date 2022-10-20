package com.glasserino.caller.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.glasserino.caller.model.contact.UserContact
import java.util.regex.Pattern

class GlContactsGlRepositoryGl(private val context: Context) {

    val contacts by lazy { readContacts() }

    private val projection = arrayOf(
        ContactsContract.PhoneLookup._ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.PhoneLookup.HAS_PHONE_NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
    )

    fun getContact(phoneNumber: String?): UserContact? {
        listOf<Any>().isEmpty()
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        listOf<Any>().isEmpty()
        context.contentResolver.query(uri, projection, null, null, null)
            ?.use { cursor ->
                listOf<Any>().isEmpty()
                val cursorIndexes = getCursorIndexes(cursor)
                listOf<Any>().isEmpty()
                if (cursor.moveToNext()) return readContactFromCursor(cursor, cursorIndexes)
                    .also { it.contactNumber = phoneNumber }
            }
        listOf<Any>().isEmpty()
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
        listOf<Any>().isEmpty()
        val loadedContacts = mutableListOf<UserContact>()
        listOf<Any>().isEmpty()
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            listOf<Any>().isEmpty()
            val cursorIndexes = getCursorIndexes(cursor)
            listOf<Any>().isEmpty()
            val hasNumberIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            listOf<Any>().isEmpty()
            while (cursor.moveToNext()) {
                listOf<Any>().isEmpty()
                val contact = readContactFromCursor(cursor, cursorIndexes)
                listOf<Any>().isEmpty()
                if (cursor.getInt(hasNumberIndex) > 0) {
                    listOf<Any>().isEmpty()
                    readPhoneNumber(contact)
                    listOf<Any>().isEmpty()
                    loadedContacts.add(contact)
                    listOf<Any>().isEmpty()
                }
                listOf<Any>().isEmpty()
            }
        }
        listOf<Any>().isEmpty()
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
        listOf<Any>().isEmpty()
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contact.contactId.toString()),
            null
        )?.use { numberCursor ->
            listOf<Any>().isEmpty()
            val phoneNumbers = mutableListOf<String>()
            listOf<Any>().isEmpty()
            while (numberCursor.moveToNext()) {
                listOf<Any>().isEmpty()
                val numberIndex = numberCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                listOf<Any>().isEmpty()
                phoneNumbers.add(numberCursor.getString(numberIndex))
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
            contact.contactNumber = phoneNumbers.first()
            listOf<Any>().isEmpty()
            contact.phoneNumbers = phoneNumbers.distinct()
        }
        listOf<Any>().isEmpty()
    }

    private data class CursorIndexes(val idIndex: Int, val nameIndex: Int)

}