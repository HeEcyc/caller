package com.paxi.cst.repository.database.dao

import androidx.room.*
import com.paxi.cst.repository.database.entity.ContactTheme
import com.paxi.cst.repository.database.entity.CustomTheme

@Dao
interface ThemeDao {

    @Insert
    fun insertCustomTheme(theme: CustomTheme)

    @Delete
    fun deleteCustomTheme(theme: CustomTheme)

    @Query("SELECT * FROM custom_themes")
    fun selectCustomThemes(): List<CustomTheme>

    @Query("SELECT (count(*) > 0) FROM custom_themes WHERE pictureFile = :themeFile")
    fun selectIsThemeCustom(themeFile: String): Boolean

    @Insert
    fun insertContactTheme(contactTheme: ContactTheme)

    @Update
    fun updateContactTheme(contactTheme: ContactTheme)

    @Query("SELECT (count(*) <> 0) FROM contacts_themes WHERE contactId = :contactId")
    fun selectContactThemeExists(contactId: Long): Boolean

    @Transaction
    fun upsertContactTheme(contactTheme: ContactTheme) {
        if (selectContactThemeExists(contactTheme.contactId))
            updateContactTheme(contactTheme)
        else
            insertContactTheme(contactTheme)
    }

    @Query("DELETE FROM contacts_themes WHERE themeFile = :themeFile")
    fun deleteContactThemeForThemeFile(themeFile: String)

    @Transaction
    fun deleteCustomThemeCompletely(theme: CustomTheme): Boolean {
        if (!selectIsThemeCustom(theme.pictureFile)) return false
        deleteCustomTheme(theme)
        deleteContactThemeForThemeFile(theme.pictureFile)
        return true
    }

    @Query("SELECT * FROM contacts_themes WHERE contactId = :contactId")
    fun selectContactThemeForId(contactId: Long): ContactTheme?

}