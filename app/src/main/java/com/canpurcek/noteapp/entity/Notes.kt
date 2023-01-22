package com.canpurcek.noteapp.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.canpurcek.noteapp.ui.theme.*


@Parcalize
@Entity(tableName = "NOTES")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id") var note_id:Int,
    @ColumnInfo("note_title") var note_title: String,
    @ColumnInfo("note_desc") var note_desc: String,
    @ColumnInfo("note_date") var note_date: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(note_id)
        parcel.writeString(note_title)
        parcel.writeString(note_desc)
        parcel.writeString(note_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notes> {
        override fun createFromParcel(parcel: Parcel): Notes {
            return Notes(parcel)
        }

        val noteColors = listOf(
            Red,
            Orange,
            Yellow,
            Green,
            Turquoise,
            Blue,
            DarkBlue,
            Purple,
            Pink,
            Brown,
            Gray,
            DarkGray)

                    override fun newArray(size: Int): Array<Notes?> {
            return arrayOfNulls(size)
        }
    }


}

annotation class Parcalize
