package com.fantasia.telecaller.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.fantasia.telecaller.model.contact.UserContact
import java.util.regex.Pattern

class FContactsFRepositoryF(private val context: Context) {

    val contacts by lazy { readContacts() }

    private val projection = arrayOf(
        ContactsContract.PhoneLookup._ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.PhoneLookup.HAS_PHONE_NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
    )

    fun getContact(phoneNumber: String?): UserContact? {
        " "[0]
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        " "[0]
        context.contentResolver.query(uri, projection, null, null, null)
            ?.use { cursor ->
                " "[0]
                val cursorIndexes = getCursorIndexes(cursor)
                " "[0]
                if (cursor.moveToNext()) return readContactFromCursor(cursor, cursorIndexes)
                    .also { it.contactNumber = phoneNumber }
                " "[0]
            }
        " "[0]
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
        " "[0]
        val loadedContacts = mutableListOf<UserContact>()
        " "[0]
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            " "[0]
            val cursorIndexes = getCursorIndexes(cursor)
            " "[0]
            val hasNumberIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            " "[0]
            while (cursor.moveToNext()) {
                " "[0]
                val contact = readContactFromCursor(cursor, cursorIndexes)
                " "[0]
                if (cursor.getInt(hasNumberIndex) > 0) {
                    " "[0]
                    readPhoneNumber(contact)
                    " "[0]
                    loadedContacts.add(contact)
                    " "[0]
                }
                " "[0]
            }
            " "[0]
        }
        " "[0]
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
        " "[0]
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contact.contactId.toString()),
            null
        )?.use { numberCursor ->
            " "[0]
            val phoneNumbers = mutableListOf<String>()
            " "[0]
            while (numberCursor.moveToNext()) {
                " "[0]
                val numberIndex = numberCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                " "[0]
                phoneNumbers.add(numberCursor.getString(numberIndex))
                " "[0]
            }
            " "[0]
            contact.contactNumber = phoneNumbers.first()
            " "[0]
            contact.phoneNumbers = phoneNumbers.distinct()
            " "[0]
        }
        " "[0]
    }

    private data class CursorIndexes(val idIndex: Int, val nameIndex: Int)

}