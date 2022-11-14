package com.example.sqllite14_11_22.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class BaseDatos(c: Context): SQLiteOpenHelper(c, DATABASE, null, VERSION) {
    companion object {
        const val VERSION = 1
        const val DATABASE = "users_db"
        const val TABLA = "users_tb"
    }

    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}