package com.angelemv.android.tiendaroom

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(StoreEntity::class), version = 1)
abstract class StoreDatabase: RoomDatabase() {
    abstract fun storeDao(): StoreDAO

}