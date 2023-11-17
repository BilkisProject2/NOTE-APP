package com.example.room

import androidx.lifecycle.LiveData

class NoteResposritory(private val noteDao: NoteDao) {

    val allNoteofinfo:LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertofinfo(note: Note){
        noteDao.insetnote(note)
    }

    suspend fun deletednoteofinfo(note: Note){
        noteDao.deltenote(note)
    }

    suspend fun  updatenoteofinfo(note: Note){
        noteDao.updatenote(note.id,note.title,note.note)
    }

}