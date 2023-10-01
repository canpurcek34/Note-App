package com.canpurcek.noteapp.retrofit.JSON

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotebookResponse(
    @SerializedName("notebook")
    @Expose
    var notebook: List<Notebook>,

    @SerializedName("success")
    @Expose
    var success : Int
)
