package com.stacky.caller.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stacky.caller.model.theme.ImageTheme

@Entity(tableName = "custom_themes")
data class CustomTheme(
    @PrimaryKey
    val pictureFile: String
) {

    fun toImageTheme() = ImageTheme(pictureFile)

}