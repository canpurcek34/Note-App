package com.canpurcek.noteapp.viewmodel

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.canpurcek.noteapp.retrofit.DAO.NotebookDaoInterface
import com.canpurcek.noteapp.retrofit.JSON.CRUDResponse
import com.canpurcek.noteapp.retrofit.JSON.Notebook
import com.canpurcek.noteapp.retrofit.JSON.NotebookResponse
import com.canpurcek.noteapp.retrofit.utils.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeScreenViewModel(application: Application): AndroidViewModel(application) {

        var notesDaoInterface: NotebookDaoInterface
        var notebook = MutableLiveData<List<Notebook>>()

    init {
        notesDaoInterface = APIUtils.getNotebookDaoInterface()
        notebook = MutableLiveData()
    }

    fun loadNotes(){

        notesDaoInterface.getNote().enqueue(object : Callback<NotebookResponse>{
            override fun onResponse(
                call: Call<NotebookResponse>,
                response: Response<NotebookResponse>
            ) {
                val notesList = response.body()!!.notebook
                    notebook.value = notesList

                for (k in notesList){
                    Log.e("*****retrofit*****","*****retrofit*****")
                    Log.e("Notebook ID",k.id.toString())
                    Log.e("Note Title",k.title)
                    Log.e("Note",k.note)
                    Log.e("Note Date",k.date)
                }
            }

            override fun onFailure(call: Call<NotebookResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun update(id:Int,title:String,note:String, date: String){
        notesDaoInterface.update(id,title,note, date).enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>, response: Response<CRUDResponse>) {
                Log.e("veritabani","update basarili")
            }
            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {
                Log.e("veritabani","update basarisiz")

            }
        })
    }
}