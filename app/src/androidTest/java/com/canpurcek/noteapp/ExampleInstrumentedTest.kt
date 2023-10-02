package com.canpurcek.noteapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.canpurcek.noteapp.retrofit.DAO.NotebookDaoInterface
import com.canpurcek.noteapp.retrofit.JSON.CRUDResponse
import com.canpurcek.noteapp.retrofit.JSON.Notebook
import com.canpurcek.noteapp.retrofit.utils.APIUtils
import com.canpurcek.noteapp.viewmodel.AddNoteScreenViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {



}