package com.canpurcek.noteapp.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.canpurcek.noteapp.entity.Notes
import com.google.gson.Gson

class NavTypo : NavType<Notes>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Notes? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Notes {
        return Gson().fromJson(value, Notes::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Notes) {
        bundle.putParcelable(key, value)
    }
}