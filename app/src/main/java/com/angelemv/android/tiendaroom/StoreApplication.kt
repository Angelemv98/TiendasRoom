package com.angelemv.android.tiendaroom

import android.app.Application
import androidx.room.Room


class StoreApplication : Application() {
    companion object {
        lateinit var database: StoreDatabase
    }

    override fun onCreate() {
        super.onCreate()
        val DATABASE_NAME = "StoreDatabase"
        database = Room.databaseBuilder(this, StoreDatabase::class.java, DATABASE_NAME).build()
    }
}