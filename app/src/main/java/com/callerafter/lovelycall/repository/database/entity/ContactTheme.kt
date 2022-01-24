package com.callerafter.lovelycall.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.callerafter.lovelycall.model.theme.ImageTheme
import com.callerafter.lovelycall.model.theme.Theme
import com.callerafter.lovelycall.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}