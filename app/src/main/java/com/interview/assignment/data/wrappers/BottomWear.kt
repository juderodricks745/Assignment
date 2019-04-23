package com.interview.assignment.data.wrappers

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BottomWear : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var filePath: String? = null

    override fun toString(): String {
        return "{" +
                "id=" + id +
                ", filePath='" + filePath + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeString(this.filePath)
    }

    constructor()

    private constructor(parcel: Parcel) {
        this.id = parcel.readInt()
        this.filePath = parcel.readString()
    }

    companion object CREATOR : Parcelable.Creator<BottomWear> {
        override fun createFromParcel(parcel: Parcel): BottomWear {
            return BottomWear(parcel)
        }

        override fun newArray(size: Int): Array<BottomWear?> {
            return arrayOfNulls(size)
        }
    }
}
