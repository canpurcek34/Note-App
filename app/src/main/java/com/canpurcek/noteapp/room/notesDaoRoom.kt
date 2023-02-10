package com.canpurcek.noteapp.room

import androidx.room.*
import com.canpurcek.noteapp.entity.Notes

@Dao
interface notesDaoRoom {


        @Query("SELECT * FROM NOTES")
        suspend fun Notes():List<Notes>

        @Insert
        suspend fun addNote(NOTES: Notes)

        @Update
        suspend fun updateNote(NOTES: Notes)

        @Delete
        suspend fun deleteNote(NOTES: Notes)

        @Query("SELECT * FROM NOTES WHERE note_desc+note_title like '%' || :word || '%'")
        suspend fun searchNote(word:String):List<Notes>




}