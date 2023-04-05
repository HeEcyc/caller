package com.paxi.cst.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paxi.cst.model.theme.ImageTheme
import com.paxi.cst.model.theme.Theme
import com.paxi.cst.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}