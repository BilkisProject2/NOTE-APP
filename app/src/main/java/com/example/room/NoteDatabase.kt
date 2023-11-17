package com.example.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun daolink(): NoteDao


    companion object {
        @Volatile

        private var INSTANCE: NoteDatabase? = null

        fun getdatabase(context: Context): NoteDatabase {

            return INSTANCE ?: synchronized(this) {
                val insta = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = insta
                insta
            }

        }
    }

}