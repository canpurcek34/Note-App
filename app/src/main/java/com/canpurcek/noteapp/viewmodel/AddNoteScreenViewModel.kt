package com.canpurcek.noteapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.canpurcek.noteapp.retrofit.DAO.NotebookDaoInterface
import com.canpurcek.noteapp.retrofit.JSON.CRUDResponse
import com.canpurcek.noteapp.retrofit.JSON.Notebook
import com.canpurcek.noteapp.retrofit.utils.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNoteScreenViewModel(application: Application): AndroidViewModel(application) {


    var notesDaoInterface: NotebookDaoInterface
    var notebook = MutableLiveData<List<Notebook>>()

    init {
        notesDaoInterface = APIUtils.getNotebookDaoInterface()
        notebook = MutableLiveData()
    }


    fun insert(title:String,note:String, date: String){
        notesDaoInterface.insert(title,note,date).enqueue(object : Callback<CRUDResponse> {
            override fun onResponse(call: Call<CRUDResponse>, response: Response<CRUDResponse>) {
                Log.e("veritabani","insert basarili")
            }
            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {
                Log.e("veritabani","insert basarisiz")

            }
        })
    }
}