package com.paxi.cst.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.paxi.cst.model.contact.UserContact
import java.util.regex.Pattern

class ContactsRepository(private val context: Context) {

    val contacts by lazy { readContacts() }

    private val projection = arrayOf(
        ContactsContract.PhoneLookup._ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.PhoneLookup.HAS_PHONE_NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
    )

    fun getContact(phoneNumber: String?): UserContact? {
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        context.contentResolver.query(uri, projection, null, null, null)
            ?.use { cursor ->
                val cursorIndexes = getCursorIndexes(cursor)
                if (cursor.moveToNext()) return readContactFromCursor(cursor, cursorIndexes)
                    .also { it.contactNumber = phoneNumber }
            }
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
        val loadedContacts = mutableListOf<UserContact>()
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val cursorIndexes = getCursorIndexes(cursor)
            val hasNumberIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            while (cursor.moveToNext()) {
                val contact = readContactFromCursor(cursor, cursorIndexes)
                if (cursor.getInt(hasNumberIndex) > 0) {
                    readPhoneNumber(contact)
                    loadedContacts.add(contact)
                }
            }
        }
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
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contact.contactId.toString()),
            null
        )?.use { numberCursor ->
            val phoneNumbers = mutableListOf<String>()
            while (numberCursor.moveToNext()) {
                val numberIndex = numberCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                phoneNumbers.add(numberCursor.getString(numberIndex))
            }
            contact.contactNumber = phoneNumbers.first()
            contact.phoneNumbers = phoneNumbers.distinct()
        }
    }

    private data class CursorIndexes(val idIndex: Int, val nameIndex: Int)

}