package com.canpurcek.noteapp.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.canpurcek.noteapp.room.dataBase
import com.canpurcek.noteapp.entity.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class notesDaoRepo(var application: Application) {

    var notesList = MutableLiveData<List<Notes>>()
    var dB: dataBase

    var notes = MutableLiveData<List<Notes>>()

    init {
        notes = MutableLiveData()

        dB = dataBase.dbAccess(application)!!
    }

    fun getNote(): MutableLiveData<List<Notes>> {
        return notes
    }

    fun getAllNotes() {
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            notes.value = dB.notesDao().Notes()
        }

    }

    fun searchNote(word: String) {

        Log.e("Not arama", word)
    }

    fun noteAdd(note_title: String, note: String, note_date : String) {

        val job: Job = CoroutineScope(Dispatchers.Main).launch {

            val newNote = Notes(0, note_title, note, note_date)
            dB.notesDao().addNote(newNote)
        }

    }

    fun noteUpdate(note_id: Int, note_title: String, note: String, note_date: String) {

        val job: Job = CoroutineScope(Dispatchers.Main).launch {

            val updateNote = Notes(note_id, note_title, note, note_date)
            dB.notesDao().updateNote(updateNote)
        }
    }

    fun noteDelete(note_id: Int) {

        val job: Job = CoroutineScope(Dispatchers.Main).launch {

            val deleteNote = Notes(note_id, note_title = "", note_desc = "", note_date = "")
            dB.notesDao().deleteNote(deleteNote)
        }
    }
}