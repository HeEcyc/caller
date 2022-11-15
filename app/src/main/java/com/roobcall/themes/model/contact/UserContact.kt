package com.roobcall.themes.model.contact

import androidx.databinding.ObservableBoolean
import java.io.Serializable

data class UserContact(
    val contactId: Long = -1,
    val contactName: String? = null,
    var contactNumber: String? = "+1 234 56 789",
    val photoThumbnailUri: String? = null,
    var phoneNumbers: List<String> = listOf()
) : Serializable

class UserContactViewModel(val contact: UserContact) {
    val isSelected = ObservableBoolean(false)
}