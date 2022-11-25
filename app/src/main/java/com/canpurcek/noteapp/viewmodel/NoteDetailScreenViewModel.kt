package com.canpurcek.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.canpurcek.noteapp.repo.notesDaoRepo

class NoteDetailScreenViewModel(application : Application): AndroidViewModel(application) {
    private var nRepo = notesDaoRepo(application)

    fun noteUpdate(note_id: Int, note_title : String, note: String,note_date : String){

        nRepo.noteUpdate(note_id, note_title, note,note_date)
    }

    fun noteDelete(note_id: Int){
        nRepo.noteDelete(note_id)
    }

}