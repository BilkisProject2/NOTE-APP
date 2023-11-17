package com.example.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(Application: Application) : AndroidViewModel(Application) {
    private val respository: NoteResposritory

    val allnotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getdatabase(Application).daolink()
        respository = NoteResposritory(dao)
        allnotes = respository.allNoteofinfo
    }

    fun deltenote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { respository.deletednoteofinfo(note) }

    fun insertnote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { respository.insertofinfo(note) }

    fun updatenote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { respository.updatenoteofinfo(note) }
}