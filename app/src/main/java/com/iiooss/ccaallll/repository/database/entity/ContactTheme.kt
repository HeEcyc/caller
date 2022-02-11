package com.iiooss.ccaallll.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iiooss.ccaallll.model.theme.ImageTheme
import com.iiooss.ccaallll.model.theme.Theme
import com.iiooss.ccaallll.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}