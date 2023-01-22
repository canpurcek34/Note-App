package com.canpurcek.noteapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canpurcek.noteapp.entity.Notes

@Database(entities = [Notes::class], version = 2)
abstract class dataBase : RoomDatabase(){

    abstract fun notesDao(): notesDaoRoom

    companion object{

        var INSTANCE : dataBase?=null

        fun dbAccess(context: Context): dataBase?{

            if (INSTANCE == null){

                synchronized(dataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    dataBase::class.java,
                    "dB.db")
                        .createFromAsset("dB.db")
                        .build()
                }

            }

            return INSTANCE
        }
    }

}