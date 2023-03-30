package com.bbbotttixxx.callscreen.model.contact

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class UserContact(
    val contactId: Long = -1,
    val contactName: String? = null,
    var contactNumber: String? = "+1 234 56 789",
    val photoThumbnailUri: String? = null,
    var phoneNumbers: List<String> = listOf()
) : Serializable, Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeLong(contactId)
        out.writeString(contactName)
        out.writeString(contactNumber)
        out.writeString(photoThumbnailUri)
        out.writeStringList(phoneNumbers)
    }

    companion object CREATOR: Parcelable.Creator<UserContact?> {
        override fun createFromParcel(`in`: Parcel): UserContact {
            return UserContact(
                `in`.readLong(),
                `in`.readString(),
                `in`.readString(),
                `in`.readString(),
                mutableListOf<String>().apply { `in`.readStringList(this) }
            )
        }

        override fun newArray(size: Int): Array<UserContact?> {
            return arrayOfNulls(size)
        }
    }
}