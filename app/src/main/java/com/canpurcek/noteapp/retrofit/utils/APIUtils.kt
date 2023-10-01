package com.canpurcek.noteapp.retrofit.utils

import com.canpurcek.noteapp.retrofit.DAO.NotebookDaoInterface
import com.canpurcek.noteapp.retrofit.client.RetrofitClient

class APIUtils {

    companion object{
        val BASE_URL = "https://emrecanpurcek.com.tr/"

        fun getNotebookDaoInterface(): NotebookDaoInterface{
            return RetrofitClient.getClient(BASE_URL).create(NotebookDaoInterface::class.java)
        }
    }
}