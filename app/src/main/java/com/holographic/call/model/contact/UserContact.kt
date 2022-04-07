package com.holographic.call.model.contact

import java.io.Serializable

data class UserContact(
    val contactId: Long = -1,
    val contactName: String? = null,
    var contactNumber: String? = "+1 234 56 789",
    val photoThumbnailUri: String? = null,
    var phoneNumbers: List<String> = listOf()
) : Serializable