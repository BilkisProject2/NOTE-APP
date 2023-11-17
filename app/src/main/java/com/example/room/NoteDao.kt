package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetnote(note: Note)

    @Delete
    suspend fun deltenote(note: Note)

    @Query("UPDATE notes_table Set title = :title , note = :note WHERE id = :id")
    suspend fun updatenote(id: Int?, title: String?, note: String?)

    @Query("SELECT * FROM notes_table order by id ASC ")
    fun getAllNotes(): LiveData<List<Note>>
}