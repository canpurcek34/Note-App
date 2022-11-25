package com.canpurcek.noteapp.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canpurcek.noteapp.viewmodel.AddNoteScreenViewModel
import com.canpurcek.noteapp.viewmodel.HomeScreenViewModel

class AddNoteScreenViewModelFactory(var application: Application):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddNoteScreenViewModel(application) as T
    }

}