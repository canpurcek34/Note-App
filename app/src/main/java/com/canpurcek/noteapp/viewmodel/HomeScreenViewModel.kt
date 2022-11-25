package com.canpurcek.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.repo.notesDaoRepo

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {
    private var nRepo = notesDaoRepo(application)
    var notes = MutableLiveData<List<Notes>>()




    init {
        loadNotes()
        notes = nRepo.getNote()
    }

    fun loadNotes(){
        nRepo.getAllNotes()
    }

    fun searchNote(word : String){
        nRepo.searchNote(word)
    }

    fun noteDelete(note_id: Int){
        nRepo.noteDelete(note_id)
    }

    fun noteUpdate(note_id: Int, note_title : String, note: String,note_date : String){

        nRepo.noteUpdate(note_id, note_title, note,note_date)
    }

}