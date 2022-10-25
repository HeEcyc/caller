package com.stacky.caller.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stacky.caller.model.theme.ImageTheme
import com.stacky.caller.model.theme.Theme
import com.stacky.caller.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}