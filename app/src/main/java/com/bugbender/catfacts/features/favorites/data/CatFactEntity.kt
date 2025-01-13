package com.bugbender.catfacts.features.favorites.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_fact")
data class CatFactEntity(
    @PrimaryKey
    @ColumnInfo(name = "text")
    var text: String = ""
)