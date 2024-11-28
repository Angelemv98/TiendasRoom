package com.angelemv.android.tiendaroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoreEntity")
data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val phone: String = "",
    val website: String = "",
    var isFavorite: Boolean = false
)
