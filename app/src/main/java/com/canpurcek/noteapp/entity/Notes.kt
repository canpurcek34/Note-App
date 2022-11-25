package com.canpurcek.noteapp.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


@Parcalize
@Entity(tableName = "NOTES")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id") @NotNull var note_id:Int,
    @ColumnInfo("note_title") @NotNull var note_title: String,
    @ColumnInfo("note_desc") @NotNull var note_desc: String,
    @ColumnInfo("note_date") @Nullable var note_date: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString()
    ) {
    }

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

        override fun newArray(size: Int): Array<Notes?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Parcalize
