package com.canpurcek.noteapp.retrofit.DAO

import com.canpurcek.noteapp.retrofit.JSON.CRUDResponse
import com.canpurcek.noteapp.retrofit.JSON.NotebookResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NotebookDaoInterface {

    @GET("notebook/get.php")
    fun getNote(): Call<NotebookResponse>

    @POST("notebook/search.php")
    @FormUrlEncoded
    fun search(@Field("title") title: String): Call<NotebookResponse>

    @POST("notebook/insert.php")
    @FormUrlEncoded
    fun insert(
        @Field("title") title: String,
        @Field("note") note: String,
        @Field("date") date: String): Call<CRUDResponse>

    @POST("notebook/delete.php")
    @FormUrlEncoded
    fun delete(@Field("id") id : Int): Call<CRUDResponse>

    @POST("notebook/update.php")
    @FormUrlEncoded
    fun update(
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("note") note: String,
        @Field("date") date: String) : Call<CRUDResponse>
}