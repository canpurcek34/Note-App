package com.canpurcek.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.canpurcek.noteapp.repo.notesDaoRepo
import com.canpurcek.noteapp.room.notesDaoRoom

class AddNoteScreenViewModel(application: Application): AndroidViewModel(application) {

    var nRepo = notesDaoRepo(application)

    fun noteAdd(note_title : String, note: String, note_date : String,note_color : String){

        nRepo.noteAdd(note_title, note, note_date, note_color)
    }

}