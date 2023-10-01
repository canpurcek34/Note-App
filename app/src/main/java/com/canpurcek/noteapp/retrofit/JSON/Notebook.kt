package com.canpurcek.noteapp.retrofit.JSON

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Notebook(
    @SerializedName("id")
    @Expose
    var id : Int,

    @SerializedName("title")
    @Expose
    var title : String,

    @SerializedName("note")
    @Expose
    var note : String,

    @SerializedName("date")
    @Expose
    var date : String
)
