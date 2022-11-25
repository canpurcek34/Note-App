package com.canpurcek.noteapp.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canpurcek.noteapp.viewmodel.HomeScreenViewModel
import com.canpurcek.noteapp.viewmodel.NoteDetailScreenViewModel

class NoteDetailsScreenViewModelFactory(var application: Application):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetailScreenViewModel(application) as T
    }

}